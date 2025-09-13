package fyi.pauli.marker.markers

import fyi.pauli.marker.Marker
import fyi.pauli.marker.geometry.Line
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.BlockDisplay
import org.bukkit.util.Transformation
import org.joml.AxisAngle4f
import org.joml.Vector3f

class LineMarker(
    val line: Line,
    var material: Material = Material.STONE,
    var thickness: Float = 0.02f,
) : Marker<BlockDisplay>(line.start) {

    override fun spawn(location: Location, builder: BlockDisplay.() -> Unit): BlockDisplay {
        return location.world.spawn(location, BlockDisplay::class.java, builder)
    }

    override fun markerSetup() {
        part {
            val vector = line.asVector().toVector3f()

            if (vector.length() == 0f)
                logger.warn("Line length for line marker cannot be zero!")

            block = material.createBlockData()

            val xAxis = Vector3f(1f, 0f, 0f)
            val rotationAxis = Vector3f(vector).cross(xAxis).normalize()
            val angle = -vector.angle(xAxis)

            val rotation = AxisAngle4f(angle, rotationAxis)

            val scale = Vector3f(vector.length(), thickness, thickness)

            transformation = Transformation(
                Vector3f(),
                rotation,
                scale,
                AxisAngle4f()
            )
        }
    }
}

fun lineMarker(line: Line, builder: LineMarker.() -> Unit = {}): LineMarker {
    return LineMarker(line).apply {
        builder()
        create()
    }
}

fun lineMarker(start: Location, end: Location, builder: LineMarker.() -> Unit = {}): LineMarker {
    return lineMarker(Line(start, end), builder)
}