package fyi.pauli.marker.provider;

import fyi.pauli.marker.renderer.impl.LineMarker;
import org.bukkit.Location;

public interface LineMarkerProvider {

    LineMarker<?> provide(Location startLocation, Location endLocation);
}
