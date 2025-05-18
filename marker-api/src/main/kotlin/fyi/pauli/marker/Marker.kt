package fyi.pauli.marker

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.entity.Display
import org.bukkit.util.Transformation

/**
 * Abstract representation of an ingame marker.
 */
abstract class Marker<D : Display>(
    open val globallyVisible: Boolean = true,
    open val color: NamedTextColor = NamedTextColor.RED,
) {

    val displayEntities: MutableSet<D> = mutableSetOf()

    internal abstract fun spawn(location: Location, builder: D.() -> Unit): D

    abstract val transformer: D.() -> Unit

    fun create(location: Location) {
        spawn(location) {
            transformer()

            glowColorOverride = Color.fromRGB(color.red(), color.green(), color.blue())
            isVisibleByDefault = globallyVisible
        }
    }
}