package fyi.pauli.marker

import fyi.pauli.marker.piston.PistonManifest
import fyi.pauli.marker.piston.VersionMeta
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.streams.*
import java.nio.file.Path
import kotlin.io.path.outputStream

suspend fun HttpClient.downloadFileAsStream(
    plugin: Bootstrap,
    version: PistonManifest.Version,
    client: VersionMeta.Downloads.Client,
): Path {
    val response = get(client.url)

    check(response.status.isSuccess()) {
       " Client file could not be downloaded. ${response.status.description}"
    }

    val channel: ByteReadChannel = response.body()

    val file = plugin.cacheDirectory.resolve("client-${version.id}-${client.sha1}.jar")

    val stream = file.outputStream().asByteWriteChannel()
    channel.copyTo(stream)

    return file
}