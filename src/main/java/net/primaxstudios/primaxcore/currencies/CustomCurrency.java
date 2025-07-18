package net.primaxstudios.primaxcore.currencies;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class CustomCurrency extends Currency {

    private final String depositCommand;
    private final String withdrawCommand;
    private final String balancePlaceholder;

    public CustomCurrency(Section section) {
        super(section);
        this.depositCommand = section.getString("deposit-command");
        this.withdrawCommand = section.getString("withdraw-command");
        this.balancePlaceholder = section.getString("balance-placeholder");
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
        Placeholder placeholder = new Placeholder(offlinePlayer);
        return Double.parseDouble(placeholder.setPlaceholders(balancePlaceholder));
    }

    private void runCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    private Placeholder getPlaceholder(OfflinePlayer offlinePlayer, double amount) {
        Placeholder placeholder = new Placeholder(offlinePlayer);
        placeholder.addReplacement("%amount%", String.valueOf(amount));
        return placeholder;
    }
}
