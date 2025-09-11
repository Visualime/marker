package fyi.pauli.marker.caching.internal

import java.nio.file.Path
import java.util.zip.ZipFile
import kotlin.io.path.*

internal fun Path.unpackJarFile(to: Path) {
    if (this.extension != "jar") return

    with(ZipFile(this.toFile())) {
        entries().asSequence().forEach { entry ->
            if (!entry.name.startsWith("assets/minecraft/models/block")) return@forEach

            val resolved = to.resolve(entry.name).createIfNotExists(directory = entry.isDirectory)
            if (resolved.isDirectory()) return@forEach

            getInputStream(entry).copyTo(resolved.outputStream())
        }
    }
}