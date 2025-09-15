package fyi.pauli.marker.control;

import fyi.pauli.marker.TestPlugin;
import fyi.pauli.marker.renderer.common.CuboidMarker;
import fyi.pauli.marker.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CuboidController extends MarkerController {
    private Location startLocation;
    private Location endLocation;

    public CuboidController(TestPlugin plugin) {
        super(plugin, ItemBuilder.of(Material.FEATHER).name(
                        CONTROLLER_NAME.append(Component.text("Cuboid").color(NamedTextColor.YELLOW))
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

        if (startLocation.x() == endLocation.x() || startLocation.y() == endLocation.y() || startLocation.z() == endLocation.z() ) {
            player.sendRichMessage("<red>The Cube must be at least 1 block apart in each direction. Please try again.");
            startLocation = null;
            endLocation = null;
            return;
        }

        if (startLocation != null && endLocation != null) {
            var marker = CuboidMarker.text(startLocation.toCenterLocation(), endLocation.toCenterLocation()).configure(textDisplayLineMarker -> {
                textDisplayLineMarker.color(plugin.getColorPicker().bukkitColor());
                textDisplayLineMarker.alignment(CuboidMarker.Alignment.EXACT);
            });

            marker.draw();
            marker.show(player, plugin);

            markers.add(marker);

            startLocation = null;
            endLocation = null;
        }
    }
}
