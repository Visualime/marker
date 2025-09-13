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
) : Marker<BlockDisplay>(spawnLocation = start) {

    override fun spawn(location: Location, builder: BlockDisplay.() -> Unit): BlockDisplay {
        return location.world.spawn(location, BlockDisplay::class.java, builder)
    }

    override fun transformer(display: BlockDisplay) {

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