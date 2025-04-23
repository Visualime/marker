package fyi.pauli.marker

import fyi.pauli.marker.model.BlockModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import org.bukkit.plugin.Plugin
import kotlin.io.path.inputStream
import kotlin.io.path.listDirectoryEntries

@OptIn(ExperimentalSerializationApi::class)
fun parseAllModels(plugin: Plugin): List<BlockModel> = buildList {
    plugin.cachedModelsPath.listDirectoryEntries().forEach { model ->
        add(json.decodeFromStream(model.inputStream()))
    }
}