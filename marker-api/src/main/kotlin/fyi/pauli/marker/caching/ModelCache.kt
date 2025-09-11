package fyi.pauli.marker.caching

import fyi.pauli.marker.caching.internal.calculateSHA1Hash
import fyi.pauli.marker.caching.internal.createIfNotExists
import fyi.pauli.marker.caching.internal.downloadClientFile
import fyi.pauli.marker.caching.internal.unpackJarFile
import fyi.pauli.marker.caching.piston.PistonManifest
import fyi.pauli.marker.caching.piston.VersionMeta
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.bukkit.Bukkit
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.*

object ModelCache {

    internal const val PISTON_VERSION_FILE: String = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"

    internal val json: Json = Json {
        prettyPrint = false
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    internal val logger: Logger = LoggerFactory.getLogger(ModelCache::class.java)

    internal val markerDirectory: Path =
        Bukkit.getServer().pluginsFolder.toPath().resolve(".marker/").createIfNotExists()
    internal val cacheDirectory: Path = markerDirectory.resolve("cache/").createIfNotExists()
    internal val cacheFile: Path = markerDirectory.resolve("cache.json").createIfNotExists {
        writeText(Json.encodeToString(Cache(mutableListOf())))
    }

    internal val httpClient: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalPathApi::class)
    fun setupModelCache(forceDownload: Boolean = false) = runBlocking {
        val serverVersion = Bukkit.getServer().minecraftVersion
        val cache = json.decodeFromStream<Cache>(cacheFile.inputStream())

        if (!forceDownload && cache.versions.contains(serverVersion)) {
            logger.info("Model assets of $serverVersion is already cached. Aborting caching processed.")
            return@runBlocking
        }

        cache.versions += serverVersion

        logger.info("Starting model asset caching of $serverVersion.")

        val pistonManifest = httpClient.get(PISTON_VERSION_FILE).body<PistonManifest>()

        val version = pistonManifest.versions.find {
            it.id == serverVersion
        } ?: throw IllegalStateException("No matching version for  found in piston manifest.")

        val versionMeta = httpClient.get(version.url).body<VersionMeta>()

        val clientFile = httpClient.downloadClientFile(version, versionMeta.downloads.client)

        val clientFileHash = clientFile.calculateSHA1Hash().joinToString { "%1$02x".format(it) }

        check(version.sha1 == clientFileHash) {
            "Calculated hash of downloaded file does not match given hash in manifest."
        }

        val versionCache = cacheDirectory.resolve(serverVersion).createIfNotExists()

        clientFile.unpackJarFile(versionCache)
        versionCache.resolve("assets/minecraft/models/block/").moveTo(versionCache.resolve("models/"))
        versionCache.resolve("assets/").deleteRecursively()

        clientFile.deleteIfExists()

        logger.info("Finished caching of assets of $serverVersion.")

        cacheFile.writeText(json.encodeToString(cache))
    }
}