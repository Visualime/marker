package fyi.pauli.marker

import fyi.pauli.marker.piston.PistonManifest
import fyi.pauli.marker.piston.Versions
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin
import java.security.MessageDigest
import kotlin.coroutines.CoroutineContext
import kotlin.io.path.readBytes

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

    override fun onLoad() {
        componentLogger.info(
            minimessage.deserialize(
                "<rainbow>Bootstrapping Marker@${this.pluginMeta.version}</rainbow>"
            )
        )

        CoroutineScope(coroutineContext).launch {
            val manifest: PistonManifest =
                client.get("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").body()

            val version = manifest.versions[0]

            val serverMinor = this@Bootstrap.server.minecraftVersion.substringBeforeLast('.')
            val pluginMinor = this@Bootstrap.pluginMeta.apiVersion?.substringBeforeLast('.')

            require(serverMinor == pluginMinor) {
                "Latest client version is not equal to the supported version. "
            }

            require(version.id == manifest.latest.snapshot) {
                "Id of newest version does not match latest snapshot id."
            }

            val versions: Versions = client.get(version.url).body()

            val file = client.downloadFileAsStream(this@Bootstrap, version, versions.downloads.client) ?: return@launch

            //TODO: Correcht SHA-1 calculation


        }
    }
}