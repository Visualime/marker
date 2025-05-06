package fyi.pauli.marker.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joml.Vector3d
import org.joml.Vector4d

@Serializable
data class BlockModel(
    val parent: String?,
    val display: Display?,
    val textures: Textures?,
    val elements: List<Element>?,
    @SerialName("ambientocclusion")
    val ambientOcclusion: Boolean?,
) {
    @Serializable
    data class Display(
        @SerialName("firstperson_righthand")
        val transformation: Transformation?,
    ) {
        @Serializable
        data class Transformation(
            @Contextual val rotation: Vector3d,
            @Contextual val translation: Vector3d,
            @Contextual val scale: Vector3d,
        )
    }

    @Serializable
    data class Element(
        @Contextual val from: Vector3d,
        @Contextual val to: Vector3d,
        val faces: Faces,
        val rotation: Rotation?,
    ) {
        @Serializable
        data class Faces(
            val down: Direction?,
            val east: Direction?,
            val north: Direction?,
            val south: Direction?,
            val up: Direction?,
            val west: Direction?,
        ) {
            @Serializable
            data class Direction(
                @Contextual val uv: Vector4d?,
                val cullface: String?,
                val rotation: Int?,
                val texture: String?,
            )
        }

        @Serializable
        data class Rotation(
            val angle: Double,
            val axis: Char,
            @Contextual val origin: Vector3d,
        )
    }

    @Serializable
    data class Textures(
        val base: String?,
        val bottom: String?,
        val front: String?,
        val particle: String?,
        val sides: String?,
        val top: String?,
        val tendrils: String?,
    )
}