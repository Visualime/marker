package fyi.pauli.marker.markers

import fyi.pauli.marker.Marker
import fyi.pauli.marker.geometry.Line
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.BlockDisplay
import kotlin.math.max
import kotlin.math.min


class CubicMarker(
    val start: Location,
    val end: Location,
    var material: Material = Material.STONE,
    var thickness: Float = 0.02f,
) : Marker<BlockDisplay>(spawnLocation = start) {

    override fun spawn(location: Location, builder: BlockDisplay.() -> Unit): BlockDisplay {
        return location.world.spawn(location, BlockDisplay::class.java, builder)
    }

    override fun markerSetup() {
        val minX: Double = min(start.x, end.x)
        val maxX: Double = max(start.x, end.x)
        val minY: Double = min(start.y, end.y)
        val maxY: Double = max(start.y, end.y)
        val minZ: Double = min(start.z, end.z)
        val maxZ: Double = max(start.z, end.z)

        val corners = listOf(
            Location(start.getWorld(), minX, minY, minZ),
            Location(start.getWorld(), maxX, minY, minZ),
            Location(start.getWorld(), minX, maxY, minZ),
            Location(start.getWorld(), maxX, maxY, minZ),
            Location(start.getWorld(), minX, minY, maxZ),
            Location(start.getWorld(), maxX, minY, maxZ),
            Location(start.getWorld(), minX, maxY, maxZ),
            Location(start.getWorld(), maxX, maxY, maxZ),
        )

        val lines = listOf(
            Line(corners[0], corners[1]),
            Line(corners[1], corners[5]),
            Line(corners[5], corners[4]),
            Line(corners[4], corners[0]),

            Line(corners[2], corners[3]),
            Line(corners[3], corners[7]),
            Line(corners[7], corners[6]),
            Line(corners[6], corners[2]),

            Line(corners[0], corners[2]),
            Line(corners[1], corners[3]),
            Line(corners[4], corners[6]),
            Line(corners[5], corners[7])
        )

        lines.forEach(::createLine)
    }

    private fun createLine(line: Line) = lineMarker(line) {
        overlayingMarker = this@CubicMarker
        material = this@CubicMarker.material
        thickness = this@CubicMarker.thickness
    }
}

fun cubicMarker(start: Location, end: Location, builder: CubicMarker.() -> Unit = {}): CubicMarker {
    return CubicMarker(start, end).apply {
        builder()
        create()
    }
}