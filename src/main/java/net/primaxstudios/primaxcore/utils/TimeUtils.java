package net.primaxstudios.primaxcore.utils;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public final class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String formatSmartDuration(Duration duration, String pattern) {
        long totalSeconds = duration.getSeconds();

        LinkedHashMap<String, Long> unitSeconds = new LinkedHashMap<>();
        unitSeconds.put("years", 365L * 24 * 60 * 60);
        unitSeconds.put("months", 30L * 24 * 60 * 60);
        unitSeconds.put("days", 24L * 60 * 60);
        unitSeconds.put("hours", 60L * 60);
        unitSeconds.put("minutes", 60L);
        unitSeconds.put("seconds", 1L);

        // Calculate all unit values from largest to smallest
        Map<String, Long> allValues = new LinkedHashMap<>();
        for (String unit : unitSeconds.keySet()) {
            long value = totalSeconds / unitSeconds.get(unit);
            totalSeconds %= unitSeconds.get(unit);
            allValues.put(unit, value);
        }

        // Now collapse units not in pattern into the next smaller unit that *is* in the pattern
        String finalPattern = pattern;
        Set<String> unitsInPattern = unitSeconds.keySet().stream()
                .filter(u -> finalPattern.contains("{" + u + "}"))
                .collect(Collectors.toSet());

        List<String> unitsList = new ArrayList<>(unitSeconds.keySet());
        for (int i = 0; i < unitsList.size(); i++) {
            String unit = unitsList.get(i);
            if (!unitsInPattern.contains(unit)) {
                // Add this unit's value to the next smaller unit present in pattern
                long valueToCollapse = allValues.get(unit);
                allValues.put(unit, 0L);

                // Find next smaller unit that is in pattern
                for (int j = i + 1; j < unitsList.size(); j++) {
                    String nextUnit = unitsList.get(j);
                    if (unitsInPattern.contains(nextUnit)) {
                        long convertedValue = valueToCollapse * (unitSeconds.get(unit) / unitSeconds.get(nextUnit));
                        allValues.put(nextUnit, allValues.get(nextUnit) + convertedValue);
                        break;
                    }
                }
            }
        }

        // Now replace placeholders only for units present in the pattern
        for (String unit : unitsInPattern) {
            pattern = pattern.replace("{" + unit + "}", String.valueOf(allValues.get(unit)));
        }

        return pattern;
    }

    public static long getSeconds(@NotNull String message) {
        if (message.contains("s")) {
            return Long.parseLong(message.replace("s", ""));
        }
        if (message.contains("m")) {
            long time = Long.parseLong(message.replace("m", ""));
            return time * 60;
        }
        if (message.contains("h")) {
            long time = Long.parseLong(message.replace("h", ""));
            return time * 60 * 60;
        }
        if (message.contains("d")) {
            long time = Long.parseLong(message.replace("d", ""));
            return time * 60 * 60 * 24;
        }
        return Long.parseLong(message);
    }

    public static int getYears(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        return period.getYears();
    }

    public static int getMonths(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        return period.getMonths();
    }

    public static int getDays(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        return period.getDays();
    }

    public static long getHours(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        Duration duration = Duration.between(from, to);
        return duration.toHours();
    }

    public static long getMinutes(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        Duration duration = Duration.between(from, to);
        return duration.toMinutes();
    }

    public static long getSeconds(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        Duration duration = Duration.between(from, to);
        return duration.getSeconds();
    }

    public static String getTime(@NotNull LocalDateTime from, @NotNull LocalDateTime to, @NotNull String year, @NotNull String month, @NotNull String day, @NotNull String hour, @NotNull String minute, @NotNull String second) {
        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        int years = period.getYears();
        if (years >= 1) return years + year;
        int months = period.getMonths();
        if (months >= 1) return months + month;
        int days = period.getDays();
        if (days >= 1) return days + day;
        Duration duration = Duration.between(from, to);
        long hours = duration.toHours();
        if (hours >= 1) return hours + hour;
        long minutes = duration.toMinutes();
        if (minutes >= 1) return minutes + minute;
        long seconds = duration.getSeconds();
        return seconds + second;
    }

    public static String getTime(@NotNull LocalDateTime from, @NotNull LocalDateTime to, @NotNull String... strings) {
        Duration duration = Duration.between(from, to);
        if (strings.length == 1) return duration.getSeconds() + strings[0];
        if (strings.length == 2) return duration.toMinutes() + strings[1];
        if (strings.length == 3) return duration.toHours() + strings[2];
        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        if (strings.length == 4) return period.getDays() + strings[3];
        if (strings.length == 5) return period.getMonths() + strings[4];
        if (strings.length == 6) return period.getYears() + strings[5];
        return null;
    }

    public static String getTimeMultiple(@NotNull LocalDateTime from, @NotNull LocalDateTime to, @NotNull String year, @NotNull String month, @NotNull String day, @NotNull String hour, @NotNull String minute, @NotNull String second) {
        StringBuilder text = new StringBuilder();
        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        Duration duration = Duration.between(from, to);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        if (years >= 1) text.append(years).append(year).append(" ");
        if (months >= 1) text.append(months).append(month).append(" ");
        if (days >= 1) text.append(days).append(day).append(" ");
        if (hours >= 1) text.append(hours).append(hour).append(" ");
        if (minutes >= 1) text.append(minutes).append(minute).append(" ");
        if (seconds >= 1) text.append(seconds).append(second);
        return text.toString();
    }
}
