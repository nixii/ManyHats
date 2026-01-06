package dev.nixii.manyhats;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ManyHats extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) return false;
        if (strings.length != 0) return false;

        ItemStack newHelm = p.getInventory().getItemInMainHand();
        p.getInventory().setItemInMainHand(p.getInventory().getHelmet());
        p.getInventory().setHelmet(newHelm);
        return true;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("hat")).setExecutor(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        getLogger().info("mguweghfiuw");
        if (e.getCursor().isEmpty()) return;
        getLogger().info("mguweghfiuw");
        if (e.getRawSlot() != 5) return; // helmet
        getLogger().info("mguweghfiuw");
        e.setCancelled(true);

        ItemStack cursor = e.getCursor();
        ItemStack helmet = p.getInventory().getHelmet();

        e.getView().setCursor(helmet);
        p.getInventory().setHelmet(cursor);
    }
}
