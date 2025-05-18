package fyi.pauli.marker.util.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.joml.Vector3d

object Vector3dListSerializer : KSerializer<Vector3d> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("org.joml.Vector3d") {
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
    }

    override fun serialize(encoder: Encoder, value: Vector3d) {
        encoder.encodeSerializableValue(
            ListSerializer(Double.serializer()), listOf(
                value.x,
                value.y,
                value.z,
            )
        )
    }

    override fun deserialize(decoder: Decoder): Vector3d {
        val list = decoder.decodeSerializableValue(ListSerializer(Double.serializer()))
        if (list.size != 3) {
            throw SerializationException("List for Vector3d must have exactly 3 elements. Got ${list.size}")
        }
        return Vector3d(list[0], list[1], list[2])
    }
}