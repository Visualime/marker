package fyi.pauli.marker.renderer.common;

import fyi.pauli.marker.renderer.Marker;
import fyi.pauli.marker.renderer.text.TextDisplaySphereMarker;
import org.bukkit.Location;

public abstract class SphereMarker<T extends Marker<T>> extends Marker<T> {
    protected final Location center;
    protected final double radius;

    protected SphereMarker(Location center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public static TextDisplaySphereMarker text(Location center, double radius) {
        return new TextDisplaySphereMarker(center, radius);
    }
}
