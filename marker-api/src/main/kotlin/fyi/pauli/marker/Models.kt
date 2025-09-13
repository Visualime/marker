package fyi.pauli.marker

import fyi.pauli.marker.caching.ModelCache
import fyi.pauli.marker.model.BlockModel
import fyi.pauli.marker.util.json
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.Plugin
import java.nio.file.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.readText

val Material.parseModel: BlockModel?
    get() {
        val path = ModelCache.cacheDirectory.resolve("${Bukkit.getServer().minecraftVersion}/").resolve(this.name.lowercase()) ?: return null
        return json.decodeFromString(path.readText())
    }