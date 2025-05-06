package fyi.pauli.marker

import fyi.pauli.marker.model.BlockModel
import org.bukkit.plugin.Plugin
import java.nio.file.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.readText

val Plugin.cachedModelsPath: Path
    get() = dataPath.parent.resolve("marker-bootstrap/cache/${server.minecraftVersion}/models/")

val Plugin.parseAllModels: List<BlockModel>
    get() = buildList {
        cachedModelsPath.listDirectoryEntries().forEach { path ->
            add(json.decodeFromString(path.readText()))
        }
    }