package fyi.pauli.marker

import fyi.pauli.marker.hash.calculateSha1Hash
import fyi.pauli.marker.piston.PistonManifest
import fyi.pauli.marker.piston.VersionMeta
import fyi.pauli.marker.unpack.unpackJarFile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path
import kotlin.coroutines.CoroutineContext
import kotlin.io.path.createDirectory
import kotlin.io.path.createParentDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.deleteRecursively
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.moveTo
import kotlin.io.path.notExists

class Bootstrap() : JavaPlugin(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = false
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }

    val minimessage = MiniMessage.miniMessage()

    val cacheDirectory: Path = dataPath.resolve("cache/").apply {
        if (parent.notExists()) createParentDirectories()
        if (notExists()) createDirectory()
    }

    override fun onLoad() {
        CoroutineScope(coroutineContext).launch {
            val manifest: PistonManifest = async {
                client.get("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").body<PistonManifest>()
            }.await()

            val version = manifest.versions.find {
                it.id == server.minecraftVersion
            } ?: throw IllegalArgumentException("No version matching this server version found in manifest.")

            val versionMeta: VersionMeta = async {
                client.get(version.url).body<VersionMeta>()
            }.await()

            val file: Path = async {
                client.downloadFileAsStream(this@Bootstrap, version, versionMeta.downloads.client)
            }.await()

            val sha1 = calculateSha1Hash(file).joinToString("") {
                "%1$02x".format(it)
            }

            check(versionMeta.downloads.client.sha1 == sha1) {
                "Calculated SHA-1 of downloaded file does not match given SHA-1 in manifest."
            }

            val versionCache = cacheDirectory.resolve("${version.id}/").apply {
                if (parent.notExists()) createParentDirectories()
                if (notExists()) createDirectory()
            }

            async {
                unpackJarFile(file, versionCache)
            }.await()

            versionCache.resolve("assets/minecraft/models/block/").moveTo(versionCache.resolve("models/"))
            versionCache.resolve("assets/").deleteIfExists()

            file.deleteIfExists()
        }
    }
}