package fyi.pauli.marker.caching.internal

import fyi.pauli.marker.caching.ModelCache
import fyi.pauli.marker.caching.piston.PistonManifest
import fyi.pauli.marker.caching.piston.VersionMeta
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.streams.*
import java.nio.file.Path
import kotlin.io.path.outputStream

internal suspend fun HttpClient.downloadClientFile(
    version: PistonManifest.Version,
    client: VersionMeta.Downloads.Client,
): Path {
    val response = this.get(client.url)

    check(response.status.isSuccess()) {
        "Client file ${version.id} could not be downloaded. Status description: '${response.status.description}'"
    }

    val clientFile = ModelCache.cacheDirectory.resolve("client-${version.id}-${client.sha1}.jar")
    response.bodyAsChannel().copyTo(clientFile.outputStream().asByteWriteChannel())

    return clientFile

}