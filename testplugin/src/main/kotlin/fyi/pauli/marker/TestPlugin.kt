package fyi.pauli.marker

import fyi.pauli.marker.types.blockMarker
import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigot
import org.bukkit.Material
import org.bukkit.entity.BlockDisplay
import org.bukkit.event.block.BlockBreakEvent

class TestPlugin : KSpigot() {

  override fun startup() {
    listen<BlockBreakEvent> {
      it.block.location.world.spawn(it.block.location, BlockDisplay::class.java) { marker ->
        marker.block = Material.BARRIER.createBlockData()
        marker.
      }
    }
  }

  override fun shutdown() {

  }
}