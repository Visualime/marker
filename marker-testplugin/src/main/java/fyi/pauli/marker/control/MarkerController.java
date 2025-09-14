package fyi.pauli.marker.control;

import fyi.pauli.marker.TestPlugin;
import fyi.pauli.marker.renderer.Marker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public abstract class MarkerController {
    protected final TestPlugin plugin;
    protected static final Component CONTROLLER_NAME = Component.text("Controller: ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY);
    private final ItemStack controllerItem;
    protected final Set<Marker<?>> markers = new HashSet<>();

    public MarkerController(TestPlugin plugin, ItemStack itemStack) {
        this.plugin = plugin;
        this.controllerItem = itemStack;
    }

    public abstract void handleClick(Player player, Location location);

    public ItemStack itemStack() {
        return controllerItem;
    }

    public boolean isUsed(ItemStack itemStack) {
        return controllerItem.equals(itemStack);
    }

    public void handleDespawn() {
        markers.forEach(Marker::despawn);
    }
}
