package fyi.pauli.marker.caching

import kotlinx.serialization.Serializable

@Serializable
data class Cache(
    val versions: MutableList<String>
)