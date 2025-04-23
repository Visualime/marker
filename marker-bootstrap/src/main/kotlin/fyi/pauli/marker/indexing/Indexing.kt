package fyi.pauli.marker.indexing

import kotlinx.serialization.Serializable

@Serializable
data class Indexing(
    var currentVersion: String,
    val versions: MutableList<String>
)