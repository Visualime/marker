package fyi.pauli.marker.renderer;

import fyi.pauli.marker.provider.LineMarkerProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public abstract class MultiLineMarker<T extends Marker<T>> extends Marker<T> {
    protected final LineMarkerProvider lineProvider;
    protected Set<Marker<?>> dependencyMarkers = new HashSet<>();

    protected MultiLineMarker(LineMarkerProvider lineProvider) {
        this.lineProvider = lineProvider;
    }

    @Override
    public void show(Player player, Plugin plugin) {
        super.show(player, plugin);

        dependencyMarkers.forEach(marker -> {
            marker.show(player, plugin);
        });
    }

    @Override
    public void hide(Player player, Plugin plugin) {
        super.hide(player, plugin);

        dependencyMarkers.forEach(marker -> {
            marker.hide(player, plugin);
        });
    }

    @Override
    public void despawn() {
        super.despawn();

        dependencyMarkers.forEach(Marker::despawn);
    }
}
