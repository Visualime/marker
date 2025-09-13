package fyi.pauli.marker

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.entity.Display
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Abstract representation of an ingame marker.
 */
abstract class Marker<D : Display>(
    private val spawnLocation: Location,
    open var color: NamedTextColor = NamedTextColor.RED,
    open var globallyVisible: Boolean = true,
    open var glowing: Boolean = true,
    protected var overlayingMarker: Marker<D>? = null,
) {

    private val marker = overlayingMarker ?: this

    protected val logger: Logger = LoggerFactory.getLogger(Marker::class.java)

    internal val displayEntities: MutableSet<D> = mutableSetOf()

    protected abstract fun markerSetup()

    protected abstract fun spawn(location: Location, builder: D.() -> Unit): D

    fun part(builder: D.() -> Unit) {
        val spawn = spawn(marker.spawnLocation) {
            glowColorOverride = Color.fromRGB(marker.color.value())
            isVisibleByDefault = marker.globallyVisible
            isGlowing = marker.glowing

            viewRange = 256f
            brightness = Display.Brightness(15, 15)
            isPersistent = false

            builder(this)
        }

        (overlayingMarker?.displayEntities ?: displayEntities) += spawn
    }

    internal fun create() {
        markerSetup()
    }
}