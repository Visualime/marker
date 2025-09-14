package fyi.pauli.marker.listener;

import fyi.pauli.marker.TestPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HotbarScrollListener implements Listener {

    private final TestPlugin plugin;

    public HotbarScrollListener(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getColorPicker().itemStack().equals(event.getPlayer().getInventory().getItem(event.getPreviousSlot()))) {
            return;
        }

        if (!player.isSneaking()) {
            return;
        }

        event.setCancelled(true);

        handleCustomHotbarAction(player, event.getPreviousSlot(), event.getNewSlot());
    }

    private void handleCustomHotbarAction(Player player, int previousSlot, int newSlot) {
        boolean scrolledRight = isScrolledRight(previousSlot, newSlot);

        plugin.getColorPicker().handleColorPick(scrolledRight);
    }

    private boolean isScrolledRight(int previousSlot, int newSlot) {
        if (previousSlot == 8 && newSlot == 0) {
            return true;
        } else if (previousSlot == 0 && newSlot == 8) {
            return false;
        } else {
            return newSlot > previousSlot;
        }
    }

    private void executeRightScrollAction(Player player) {
        // Beispiel: Teleportiere den Spieler nach oben
        player.teleport(player.getLocation().add(0, 1, 0));
        player.sendMessage("§eNach oben teleportiert!");
    }

    private void executeLeftScrollAction(Player player) {
        // Beispiel: Gib dem Spieler ein Item
        player.getInventory().addItem(new org.bukkit.inventory.ItemStack(
                org.bukkit.Material.DIAMOND, 1));
        player.sendMessage("§bDiamant erhalten!");
    }
}

// Registration in deiner Main-Klasse:
// getServer().getPluginManager().registerEvents(new HotbarScrollListener(this), this);
