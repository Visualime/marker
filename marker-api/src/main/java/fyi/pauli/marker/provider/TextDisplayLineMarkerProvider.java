package fyi.pauli.marker.provider;

import fyi.pauli.marker.renderer.impl.LineMarker;
import org.bukkit.Location;

public class TextDisplayLineMarkerProvider implements LineMarkerProvider {
  public static final TextDisplayLineMarkerProvider INSTANCE = new TextDisplayLineMarkerProvider();

  @Override
  public LineMarker<?> provide(Location startLocation, Location endLocation) {
    return LineMarker.text(startLocation, endLocation);
  }
}