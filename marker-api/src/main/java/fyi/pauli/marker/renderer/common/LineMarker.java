package fyi.pauli.marker.renderer.common;

import fyi.pauli.marker.renderer.Marker;
import fyi.pauli.marker.renderer.block.BlockDisplayLineMarker;
import fyi.pauli.marker.renderer.text.TextDisplayLineMarker;
import org.bukkit.Location;

public abstract class LineMarker<T extends LineMarker<T>> extends Marker<T> {
    protected final Location startLocation;
    protected final Location endLocation;

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
}
