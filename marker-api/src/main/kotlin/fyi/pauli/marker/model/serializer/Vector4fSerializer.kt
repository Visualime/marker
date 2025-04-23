package fyi.pauli.marker.model.serializer

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.joml.Vector4f

object Vector4fSerializer : KSerializer<Vector4f> {

    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("org.joml.Vector4f") {
        element<Float>("x")
        element<Float>("y")
        element<Float>("z")
        element<Float>("w")
    }

    override fun serialize(encoder: Encoder, value: Vector4f) = encoder.encodeStructure(descriptor) {
        encodeFloatElement(descriptor, 0, value.x)
        encodeFloatElement(descriptor, 1, value.y)
        encodeFloatElement(descriptor, 2, value.z)
        encodeFloatElement(descriptor, 3, value.w)
    }

    override fun deserialize(decoder: Decoder): Vector4f = decoder.decodeStructure(descriptor) {
        Vector4f(
            decoder.decodeFloat(),
            decoder.decodeFloat(),
            decoder.decodeFloat(),
            decoder.decodeFloat(),
        )
    }
}