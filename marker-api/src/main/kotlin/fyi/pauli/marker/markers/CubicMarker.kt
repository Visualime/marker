package fyi.pauli.marker.markers

import fyi.pauli.marker.Marker
import fyi.pauli.marker.geometry.Line
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.BlockDisplay

class CubicMarker(
    val start: Location,
    val end: Location,
    var material: Material = Material.STONE,
    var thickness: Float = 0.02f,
) : Marker<BlockDisplay>(start) {

    override fun spawn(location: Location, builder: BlockDisplay.() -> Unit): BlockDisplay {
        return location.world.spawn(location, BlockDisplay::class.java, builder)
    }

    override fun markerSetup() {
        val world = start.world

        val minX = minOf(start.x, end.x)
        val maxX = maxOf(start.x, end.x)
        val minY = minOf(start.y, end.y)
        val maxY = maxOf(start.y, end.y)
        val minZ = minOf(start.z, end.z)
        val maxZ = maxOf(start.z, end.z)

        val corners = listOf(
            Location(world, minX, minY, minZ),
            Location(world, minX, minY, maxZ),
            Location(world, minX, maxY, minZ),
            Location(world, minX, maxY, maxZ),
            Location(world, maxX, minY, minZ),
            Location(world, maxX, minY, maxZ),
            Location(world, maxX, maxY, minZ),
            Location(world, maxX, maxY, maxZ)
        )
        listOf(
            Line(corners[0], corners[2]),
            Line(corners[1], corners[3]),
            Line(corners[4], corners[6]),
            Line(corners[5], corners[7]),

            Line(corners[0], corners[4]),
            Line(corners[1], corners[5]),
            Line(corners[2], corners[6]),
            Line(corners[3], corners[7]),

            Line(corners[0], corners[1]),
            Line(corners[4], corners[5]),
            Line(corners[2], corners[3]),
            Line(corners[6], corners[7])
        ).forEach(::createLine)
    }

    private fun createLine(line: Line) = lineMarker(line) {
        overlayingMarker = this@CubicMarker
        material = this@CubicMarker.material
        thickness = this@CubicMarker.thickness
    }
}

fun cubicMarker(line: Line, builder: CubicMarker.() -> Unit = {}): CubicMarker {
    return CubicMarker(line.start, line.end).apply {
        builder()
        create()
    }
}

fun cubicMarker(start: Location, end: Location, builder: CubicMarker.() -> Unit = {}): CubicMarker {
    return cubicMarker(Line(start, end), builder)
}