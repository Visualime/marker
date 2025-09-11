package fyi.pauli.marker.caching.piston

import kotlinx.serialization.Serializable

@Serializable
data class PistonManifest(
    val latest: Latest,
    val versions: List<Version>,
) {

    @Serializable
    data class Latest(
        val release: String,
        val snapshot: String,
    )

    @Serializable
    data class Version(
        val id: String,
        val sha1: String,
        val url: String,
    )
}