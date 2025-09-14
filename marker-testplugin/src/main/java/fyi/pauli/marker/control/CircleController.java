package fyi.pauli.marker.control;

import fyi.pauli.marker.TestPlugin;
import fyi.pauli.marker.renderer.common.CircleMarker;
import fyi.pauli.marker.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CircleController extends MarkerController {
    private Location startLocation;
    private Location endLocation;

    public CircleController(TestPlugin plugin) {
        super(plugin, ItemBuilder.of(Material.SNOWBALL).name(
                        CONTROLLER_NAME.append(Component.text("Circle").color(NamedTextColor.YELLOW))
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
            var radius = startLocation.distance(endLocation);

            var marker = CircleMarker.text(startLocation.toCenterLocation(), radius).configure(textDisplayLineMarker -> {
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
