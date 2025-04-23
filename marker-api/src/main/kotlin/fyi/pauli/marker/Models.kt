package fyi.pauli.marker

import org.bukkit.plugin.Plugin
import java.nio.file.Path

val Plugin.cachedModelsPath: Path
    get() = dataPath.parent.resolve("marker-bootstrap/cache/${server.minecraftVersion}/models/")