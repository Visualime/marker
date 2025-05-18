package fyi.pauli.marker.markers

import fyi.pauli.marker.Line
import fyi.pauli.marker.Marker
import org.bukkit.Location
import org.bukkit.entity.BlockDisplay
import org.bukkit.util.Transformation
import org.joml.AxisAngle4f
import org.joml.Vector3d
import org.joml.Vector3f

class LineMarker(
    val line: Line,
    val thickness: Float = 0.02f,
) : Marker<BlockDisplay>() {

    override fun spawn(location: Location, builder: BlockDisplay.() -> Unit): BlockDisplay {
        return location.world.spawn(location, BlockDisplay::class.java, builder)
    }

    override val transformer: BlockDisplay.() -> Unit = {
        val xAxis = Vector3d(1.0, 0.0, 0.0)
        val rotationAxis = line.asVector().cross(xAxis).normalize()
        val angleToXAxis = -line.asVector().angle(xAxis).toFloat()

        transformation = Transformation(
            Vector3f(),
            AxisAngle4f(angleToXAxis, rotationAxis.x.toFloat(), rotationAxis.y.toFloat(), rotationAxis.z.toFloat()),
            Vector3f(line.asVector().length().toFloat(), thickness, thickness),
            AxisAngle4f()
        )
    }
}