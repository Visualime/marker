package fyi.pauli.marker.provider;

import fyi.pauli.marker.MarkerProvider;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BlockDisplayProvider implements MarkerProvider<BlockDisplay> {

  @Override
  public @NotNull BlockDisplay createEntity(Location location, @NotNull Consumer<BlockDisplay> builder) {
    return location.getWorld().spawn(location, BlockDisplay.class, builder);
  }
}