package fyi.pauli.marker.geometry

import org.bukkit.Location
import org.bukkit.util.Vector

class Line(
    val start: Location,
    val end: Location,
) {

    fun asVector(): Vector {
        return end.toVector().clone().subtract(start.toVector())
    }
}