package fyi.pauli.marker.renderer.common;

import fyi.pauli.marker.renderer.Marker;
import fyi.pauli.marker.renderer.text.TextDisplayCuboidMarker;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public abstract class CuboidMarker<T extends Marker<T>> extends Marker<T> {
    protected Alignment alignment = Alignment.EXACT;
    protected final Location startLocation;
    protected final Location endLocation;
    protected final Set<LineMarker<?>> edges = new HashSet<>();

    protected CuboidMarker(Location startLocation, Location endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public static TextDisplayCuboidMarker text(Location startLocation, Location endLocation) {
        return new TextDisplayCuboidMarker(startLocation, endLocation);
    }

    public enum Alignment {
        INCLUSIVE, EXCLUSIVE, EXACT;
    }

    public void alignment(Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public void despawn() {
        edges.forEach(Marker::despawn);
    }
}
