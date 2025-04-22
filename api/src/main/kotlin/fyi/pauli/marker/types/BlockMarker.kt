package fyi.pauli.marker.types

import fyi.pauli.marker.Marker
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.Display
import org.bukkit.plugin.Plugin
import org.bukkit.util.BoundingBox
import org.bukkit.util.Transformation
import org.joml.AxisAngle4f
import org.joml.Vector3f

open class BlockMarker(
  override val plugin: Plugin,
  override val world: World,
  open var material: Material = Material.BARRIER,
  var area: BoundingBox,
) : Marker<BlockDisplay>(plugin, world) {

  override fun spawnEntity(location: Location, builder: BlockDisplay.() -> Unit): BlockDisplay {
    return world.spawn(location, BlockDisplay::class.java, builder)
  }

  override val transformMarker: BlockDisplay.() -> Unit = {
    block = material.createBlockData()

    transformation = scale {
      x = area.widthX.toFloat()
      y = area.height.toFloat()
      z = area.widthZ.toFloat()
    }
  }

  private fun scale(vec: Vector3f.() -> Unit) = Transformation(Vector3f(), AxisAngle4f(), Vector3f().apply(vec), AxisAngle4f())
}