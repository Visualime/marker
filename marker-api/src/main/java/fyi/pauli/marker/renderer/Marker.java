package fyi.pauli.marker.renderer;

import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class Marker<T extends Marker<T>> {
    protected Color color = Color.LIME;
    protected boolean globallyVisible = false;
    protected boolean glowing = true;
    protected Set<Entity> markerEntities = new HashSet<>();
    protected Set<Marker<?>> dependencyMarkers = new HashSet<>();

    public abstract T draw();

    public void show(Player player, Plugin plugin) {
        markerEntities.forEach(entity -> {
            player.showEntity(plugin, entity);
        });

        dependencyMarkers.forEach(marker -> {
            marker.show(player, plugin);
        });
    }

    public void show(Collection<Player> players, Plugin plugin) {
        players.forEach(player -> show(player, plugin));
    }

    public void hide(Player player, Plugin plugin) {
        markerEntities.forEach(entity -> {
            player.hideEntity(plugin, entity);
        });

        dependencyMarkers.forEach(marker -> {
            marker.hide(player, plugin);
        });
    }

    public void hide(Collection<Player> players, Plugin plugin) {
        players.forEach(player -> hide(player, plugin));
    }

    public void color(Color color) {
        this.color = color;
    }

    public void globallyVisible(boolean globallyVisible) {
        this.globallyVisible = globallyVisible;
    }

    public void glowing(boolean glowing) {
        this.glowing = glowing;
    }

    public T configure(Consumer<T> consumer) {
        consumer.accept(self());
        return self();
    }

    private T self() {
        return (T) this;
    }

    public void despawn() {
        markerEntities.forEach(Entity::remove);
        markerEntities.clear();
        dependencyMarkers.forEach(Marker::despawn);
        dependencyMarkers.clear();
    }

    public T inheritProps(Marker<?> marker) {
        this.color = marker.color;
        this.globallyVisible = marker.globallyVisible;
        this.glowing = marker.glowing;
        return self();
    }
}
