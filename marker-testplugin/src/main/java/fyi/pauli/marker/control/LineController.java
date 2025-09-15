package fyi.pauli.marker.control;

import fyi.pauli.marker.TestPlugin;
import fyi.pauli.marker.renderer.impl.LineMarker;
import fyi.pauli.marker.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LineController extends MarkerController {
    private Location startLocation;
    private Location endLocation;

    public LineController(TestPlugin plugin) {
        super(plugin, ItemBuilder.of(Material.STICK).name(
                        CONTROLLER_NAME.append(Component.text("Line").color(NamedTextColor.YELLOW))
                ).item()
        );
    }

    @Override
    public void handleClick(Player player, Location location) {
        if (startLocation == null) {
            startLocation = location;
        } else {
            endLocation = location;
        }

        if (startLocation != null && endLocation != null) {
            var marker = LineMarker.block(startLocation.toCenterLocation(), endLocation.toCenterLocation()).configure(textDisplayLineMarker -> {
                textDisplayLineMarker.color(plugin.getColorPicker().bukkitColor());
            });

            marker.draw();
            marker.show(player, plugin);

            markers.add(marker);

            startLocation = null;
            endLocation = null;
        }
    }
}
