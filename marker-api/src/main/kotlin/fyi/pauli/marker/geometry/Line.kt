package fyi.pauli.marker.geometry

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Location
import org.joml.Vector3d
import org.joml.Vector3f

@Serializable
data class Line(
    @Contextual val start: Location,
    @Contextual val end: Location,
) {

    fun asVector(): Vector3d = end.subtract(start).toVector3d()
}

fun Location.toVector3d(): Vector3d = Vector3d(this.x, this.y, this.z)

operator fun Location.rangeTo(end: Location): Line = Line(this, end)
