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
import org.joml.Vector3f

object Vector3fSerializer : KSerializer<Vector3f> {

    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("org.joml.Vector3f") {
        element<Float>("x")
        element<Float>("y")
        element<Float>("z")
    }

    override fun serialize(encoder: Encoder, value: Vector3f) = encoder.encodeStructure(descriptor) {
        encodeFloatElement(descriptor, 0, value.x)
        encodeFloatElement(descriptor, 1, value.y)
        encodeFloatElement(descriptor, 2, value.z)
    }

    override fun deserialize(decoder: Decoder): Vector3f = decoder.decodeStructure(descriptor) {
        Vector3f(
            decoder.decodeFloat(),
            decoder.decodeFloat(),
            decoder.decodeFloat(),
        )
    }
}