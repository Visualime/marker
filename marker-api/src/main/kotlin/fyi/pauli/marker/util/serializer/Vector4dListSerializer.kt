package fyi.pauli.marker.util.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.joml.Vector4d

object Vector4dListSerializer : KSerializer<Vector4d> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("org.joml.Vector4d") {
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
        element<Double>("w")
    }

    override fun serialize(encoder: Encoder, value: Vector4d) {
        encoder.encodeSerializableValue(
            ListSerializer(Double.serializer()), listOf(
                value.x,
                value.y,
                value.z,
                value.w,
            )
        )
    }

    override fun deserialize(decoder: Decoder): Vector4d {
        val list = decoder.decodeSerializableValue(ListSerializer(Double.serializer()))
        if (list.size != 4) {
            throw SerializationException("List for Vector4d must have exactly 4 elements. Got ${list.size}")
        }
        return Vector4d(list[0], list[1], list[2], list[3])
    }
}