package dev.nixii.manyhats;

import dev.nixii.manpage.ManPage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

// manual page, if you have that
		if (this.getServer().getPluginManager().getPlugin("ManPage") != null) {
			getLogger().info("hmmmm");
			ManPage.addManPage(this, "hat", (Player player) -> {
				player.sendMessage(
					Component.text("/man hat:hat")
						.decoration(TextDecoration.BOLD, true)
						.color(TextColor.color(0x00FF00))
						.appendNewline().appendNewline()
						.append(
							Component.text("| In Survival and Adventure mode, you can put" +
                                                                        " any item you want" +
									" on your head like a helmet.")
								.color(TextColor.color(0xffffff))
								.decoration(TextDecoration.BOLD, false))
						.appendNewline()
						.append(
							Component.text("| You can run /hat to swap your held item and" +
                                                                        " helmet in any " +
								"game mode.")
                                                        .color(TextColor.color(0xffffff))
                                                        .decoration(TextDecoration.BOLD, false))
				);
			});
                        ManPage.alias(this, "hat", "hat");
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player p)) return;
		if (!(e.getWhoClicked().hasPermission("manyhats.hat"))) return;
		if (e.getCursor().isEmpty()) return;
		if (e.getRawSlot() != 5) return; // helmet slot
		e.setCancelled(true);

		ItemStack cursor = e.getCursor();
		ItemStack helmet = p.getInventory().getHelmet();

		e.getView().setCursor(helmet);
		p.getInventory().setHelmet(cursor);
	}
}
