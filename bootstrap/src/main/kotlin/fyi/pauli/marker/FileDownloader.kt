package fyi.pauli.marker

import fyi.pauli.marker.piston.PistonManifest
import fyi.pauli.marker.piston.Versions
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.streams.*
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists
import kotlin.io.path.outputStream

suspend fun HttpClient.downloadFileAsStream(
    plugin: Bootstrap,
    version: PistonManifest.Version,
    client: Versions.Downloads.Client,
): Path? {
    val response = get(client.url)

    if (!response.status.isSuccess()) {
        plugin.componentLogger.error(plugin.minimessage.deserialize("<red>Client file could not be downloaded. ${response.status.description}"))
        return null
    }

    val channel: ByteReadChannel = response.body()

    val cacheDirectory = plugin.dataPath.absolute().resolve("cache/").apply {
        if (parent.notExists()) parent.createDirectory()
        if (notExists()) createDirectory()
    }

    val file = cacheDirectory.resolve("client-${version.id}-${client.sha1}.jar")

    val stream = file.outputStream().asByteWriteChannel()
    channel.copyTo(stream)

    return file

}