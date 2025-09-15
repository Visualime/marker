package fyi.pauli.marker.provider;

import fyi.pauli.marker.renderer.impl.LineMarker;
import org.bukkit.Location;

public class BlockDisplayLineMarkerProvider implements LineMarkerProvider {
  public static final BlockDisplayLineMarkerProvider INSTANCE = new BlockDisplayLineMarkerProvider();

  @Override
  public LineMarker<?> provide(Location startLocation, Location endLocation) {
    return LineMarker.block(startLocation, endLocation);
  }
}