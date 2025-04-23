package fyi.pauli.marker

import fyi.pauli.marker.model.serializer.Vector3fSerializer
import fyi.pauli.marker.model.serializer.Vector4fSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual


internal val json = Json {
    ignoreUnknownKeys = false
    explicitNulls = false

    serializersModule = SerializersModule {
        contextual(Vector3fSerializer)
        contextual(Vector4fSerializer)
    }
}