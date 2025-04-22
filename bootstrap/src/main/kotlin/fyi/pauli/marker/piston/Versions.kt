package fyi.pauli.marker.piston

import kotlinx.serialization.Serializable

@Serializable
data class Versions(
    val downloads: Downloads
)  {

    @Serializable
    data class Downloads(
        val client: Client
    ) {

        @Serializable
        data class Client(
            val sha1: String,
            val size: Int,
            val url: String
        )
    }
}