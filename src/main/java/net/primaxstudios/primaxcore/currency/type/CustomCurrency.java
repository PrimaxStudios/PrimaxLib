package net.primaxstudios.primaxcore.currency.type;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.currency.Currency;
import net.primaxstudios.primaxcore.placeholder.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class CustomCurrency implements Currency {

    private final String depositCommand;
    private final String withdrawCommand;
    private final String balancePlaceholder;

    public CustomCurrency(String depositCommand, String withdrawCommand, String balancePlaceholder) {
        this.depositCommand = depositCommand;
        this.withdrawCommand = withdrawCommand;
        this.balancePlaceholder = balancePlaceholder;
    }

    public static CustomCurrency fromSection(Section section) {
        String depositCommand = section.getString("deposit_command");
        String withdrawCommand = section.getString("withdraw_command");
        String balancePlaceholder = section.getString("balance_placeholder");
        return new CustomCurrency(depositCommand, withdrawCommand, balancePlaceholder);
    }

    @Override
    public boolean deposit(OfflinePlayer offlinePlayer, double amount) {
        Placeholder placeholder = getPlaceholder(offlinePlayer, amount);
        runCommand(placeholder.setPlaceholders(depositCommand));
        return true;
    }

    @Override
    public boolean withdraw(OfflinePlayer offlinePlayer, double amount) {
        Placeholder placeholder = getPlaceholder(offlinePlayer, amount);
        runCommand(placeholder.setPlaceholders(withdrawCommand));
        return true;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        Placeholder placeholder = getPlaceholder(offlinePlayer);
        return Double.parseDouble(placeholder.setPlaceholders(balancePlaceholder));
    }

    private void runCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    private Placeholder getPlaceholder(OfflinePlayer offlinePlayer) {
        Placeholder placeholder = new Placeholder();
        placeholder.addReplacement("{player_name}", offlinePlayer.getName());
        return placeholder;
    }

    private Placeholder getPlaceholder(OfflinePlayer offlinePlayer, double amount) {
        Placeholder placeholder = getPlaceholder(offlinePlayer);
        placeholder.addReplacement("{amount}", String.valueOf(amount));
        return placeholder;
    }
}
