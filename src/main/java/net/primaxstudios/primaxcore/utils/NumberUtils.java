package net.primaxstudios.primaxcore.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public final class NumberUtils {

    private NumberUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    public static String format(double number) {
        return formatter.format(number);
    }

    public static String toRoman(int number) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(symbols[i]);
            }
        }
        return result.toString();
    }

    private static String formatMoney(double number) {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(number);
    }

    public static String getMoney(double amount) {
        if (amount < 1000.0) return formatMoney(amount);
        if (amount < 1000000.0) return formatMoney(amount / 1000.0) + "k";
        if (amount < 1.0E9) return formatMoney(amount / 1000000.0) + "M";
        if (amount < 1.0E12) return formatMoney(amount / 1.0E9) + "B";
        if (amount < 1.0E15) return formatMoney(amount / 1.0E12) + "T";
        if (amount < 1.0E18) return formatMoney(amount / 1.0E15) + "Q";
        return String.valueOf((long) amount);
    }

    public static double getMoney(String message) {
        try {
            String suffix = message.substring(message.length() - 1); // Get the last character as the suffix
            String value = message.substring(0, message.length() - 1); // Get the value without the suffix

            double multiplier;

            switch (suffix.toLowerCase()) {
                case "k":
                    multiplier = 1000;
                    break;
                case "m":
                    multiplier = 1000000;
                    break;
                case "b":
                    multiplier = 1000000000;
                    break;
                case "t":
                    multiplier = 1000000000000.0;
                    break;
                case "q":
                    multiplier = 1000000000000000.0;
                    break;
                default:
                    return Double.parseDouble(message); // No suffix found, directly parse the value as double
            }
            return Double.parseDouble(value) * multiplier;
        } catch (NumberFormatException | StringIndexOutOfBoundsException exp) {
            return 0;
        }
    }
}
