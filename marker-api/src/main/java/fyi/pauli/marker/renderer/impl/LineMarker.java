package fyi.pauli.marker.renderer.impl;

import fyi.pauli.marker.renderer.Marker;
import org.bukkit.Location;

public abstract class LineMarker<T extends LineMarker<T>> extends Marker<T> {
    protected Location startLocation;
    protected Location endLocation;

    protected LineMarker() {

    }

    protected LineMarker(Location startLocation, Location endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public static TextDisplayLineMarker text(Location startLocation, Location endLocation) {
        return new TextDisplayLineMarker(startLocation, endLocation);
    }

    public static BlockDisplayLineMarker block(Location startLocation, Location endLocation) {
        return new BlockDisplayLineMarker(startLocation, endLocation);
    }

    protected void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    protected void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
}
