package fyi.pauli.marker.markers

import fyi.pauli.marker.Marker
import fyi.pauli.marker.geometry.Line
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.BlockDisplay
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
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
            block = material.createBlockData()

            val vector = line.asVector()

            val xAxis = Vector(1, 0, 0)
            var rotationAxis = vector.clone().crossProduct(xAxis).normalize()
            val angle = -vector.angle(xAxis)

            if (vector.angle(xAxis) == 0f)
                rotationAxis = Vector(0, 0, 0)

            val rotation = AxisAngle4f(angle, rotationAxis.toVector3f())

            val scale = Vector3f(vector.length().toFloat(), thickness, thickness)

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