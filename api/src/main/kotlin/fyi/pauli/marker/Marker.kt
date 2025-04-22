package fyi.pauli.marker

import org.bukkit.*
import org.bukkit.entity.Display
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import org.bukkit.util.Transformation
import org.joml.AxisAngle4f
import org.joml.Vector3f
import java.util.*

abstract class Marker<D : Display>(
  open val plugin: Plugin,
  open val world: World,
  open var color: Color = Color.WHITE,
  open var isGloballyVisible: Boolean = false,
  open var isVisible: Boolean = true,
  open var exposedTo: MutableSet<UUID> = mutableSetOf(),
) {

  companion object {
    var namespacedKey = NamespacedKey.minecraft("is_marker")
  }

  fun createMarker(location: Location): Entity {
    return spawnEntity(location) {
      isVisibleByDefault = isGloballyVisible
      if (!isGloballyVisible && isVisible) exposedTo.forEach { uuid ->
        Bukkit.getPlayer(uuid)?.showEntity(plugin, this)
      }

      glowColorOverride = color
      isGlowing = isVisible
      isInvulnerable = true
      isPersistent = true
      brightness = Display.Brightness(15, 15)
      shadowStrength = 0f
      shadowRadius = 0f

      persistentDataContainer.set(namespacedKey, PersistentDataType.BOOLEAN, true)

      transformMarker(this)
    }
  }

  abstract fun spawnEntity(location: Location, builder: D.() -> Unit): D

  abstract val transformMarker: D.() -> Unit

}