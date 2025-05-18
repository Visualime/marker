package fyi.pauli.marker.util

import fyi.pauli.marker.util.serializer.Vector3dListSerializer
import fyi.pauli.marker.util.serializer.Vector4dListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

val json: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    isLenient = true

    serializersModule = SerializersModule {
        contextual(Vector3dListSerializer)
        contextual(Vector4dListSerializer)
    }
}
