package fyi.pauli.marker.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joml.Vector3f
import org.joml.Vector4i

@Serializable
data class BlockModel(
    val parent: String? = null,
    val display: Display? = null,
    val textures: Textures,
    val elements: List<Element>? = null,
    val ambientOcclusion: Boolean? = null
) {
    @Serializable
    data class Display(
        @SerialName("firstperson_righthand")
        val transformation: Transformation,
    ) {
        @Serializable
        data class Transformation(
            @Contextual val rotation: Vector3f,
            @Contextual val scale: Vector3f,
            @Contextual val translation: Vector3f,
        )
    }

    @Serializable
    data class Element(
        @Contextual val from: Vector3f,
        @Contextual val to: Vector3f,
        val faces: Faces,
        val rotation: Rotation? = null,
    ) {
        @Serializable
        data class Faces(
            val down: Direction,
            val east: Direction,
            val north: Direction,
            val south: Direction,
            val up: Direction,
            val west: Direction,
        ) {
            @Serializable
            data class Direction(
                @Contextual val uv: Vector4i,
                val cullface: String,
                val rotation: Int?,
                val texture: String,
            )
        }

        @Serializable
        data class Rotation(
            val angle: Double,
            val axis: Char,
            @Contextual val origin: Vector3f,
        )
    }

    @Serializable
    data class Textures(
        val base: String? = null,
        val bottom: String? = null,
        val front: String? = null,
        val particle: String? = null,
        val sides: String? = null,
        val top: String? = null,
        val tendrils: String? = null,
    )
}