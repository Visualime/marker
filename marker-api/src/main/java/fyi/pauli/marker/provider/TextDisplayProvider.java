package fyi.pauli.marker.provider;

import fyi.pauli.marker.MarkerProvider;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TextDisplayProvider implements MarkerProvider<TextDisplay> {

  @Override
  public @NotNull TextDisplay createEntity(Location location, @NotNull Consumer<TextDisplay> builder) {
    return location.getWorld().spawn(location, TextDisplay.class, builder);
  }
}