package net.primaxstudios.primaxcore.currencies;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.PlaceholderString;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class CustomCurrency extends Currency {

    private final PlaceholderString depositCommand;
    private final PlaceholderString withdrawCommand;
    private final PlaceholderString balancePlaceholder;

    public CustomCurrency(Section section) {
        super(section);
        this.depositCommand = new PlaceholderString(section.getString("deposit-command"));
        this.withdrawCommand = new PlaceholderString(section.getString("withdraw-command"));
        this.balancePlaceholder = new PlaceholderString(section.getString("balance-placeholder"));
    }

    @Override
    public boolean deposit(OfflinePlayer offlinePlayer, double amount) {
        runCommand(depositCommand.getObject(getPlaceholder(offlinePlayer, amount)));
        return true;
    }

    @Override
    public boolean withdraw(OfflinePlayer offlinePlayer, double amount) {
        runCommand(withdrawCommand.getObject(getPlaceholder(offlinePlayer, amount)));
        return false;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return Double.parseDouble(balancePlaceholder.getObject(new Placeholder(offlinePlayer)));
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
