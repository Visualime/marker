package fyi.pauli.marker.geometry

import org.bukkit.Location
import org.bukkit.util.Vector

class Line(
    private val startLocation: Location,
    private val endLocation: Location,
) {

    val start: Location
        get() {
            val vector1 = Vector(1, 1, 1)

            val endVector = endLocation.toVector()
            val startVector = startLocation.toVector()

            if (endVector.dot(vector1) < startVector.dot(vector1))
                return endLocation.clone()

            return startLocation.clone()
        }

    val end: Location
        get() {
            val vector1 = Vector(1, 1, 1)

            val endVector = endLocation.toVector()
            val startVector = startLocation.toVector()

            if (endVector.dot(vector1) < startVector.dot(vector1))
                return startLocation.clone()

            return endLocation.clone()
        }

    fun asVector(): Vector {
        return end.toVector().clone().subtract(start.toVector())
    }
}