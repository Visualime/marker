package fyi.pauli.marker.renderer.common;

import fyi.pauli.marker.renderer.Marker;
import fyi.pauli.marker.renderer.text.TextDisplayCircleMarker;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public abstract class CircleMarker<T extends Marker<T>> extends Marker<T> {
    protected static final int DEFAULT_SEGMENTS = 16;
    protected final Location center;
    protected final double radius;
    protected Orientation orientation = Orientation.HORIZONTAL;

    protected CircleMarker(Location center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public static TextDisplayCircleMarker text(Location center, double radius) {
        return new TextDisplayCircleMarker(center, radius);
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL;
    }

    public void orientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
