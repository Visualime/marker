package fyi.pauli.marker.geometry

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.util.Vector

@Serializable
data class Line(
    @Contextual val start: Location,
    @Contextual val end: Location,
) {

    fun asVector(): Vector = end.subtract(start).toVector()
}