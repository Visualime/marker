package fyi.pauli.marker

import fyi.pauli.marker.util.serializer.Vector3dListSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.joml.Vector3d

@Serializable
data class Line(
    @Contextual val start: Vector3d,
    @Contextual val end: Vector3d,
) {

    constructor(start: Location, end: Location) : this(start.toVector().toVector3d(), end.toVector().toVector3d())

    fun asVector(): Vector3d = end.min(start)
}