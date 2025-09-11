package fyi.pauli.marker.caching.internal

import java.nio.file.Path
import java.util.zip.ZipFile
import kotlin.io.path.extension
import kotlin.io.path.isDirectory
import kotlin.io.path.outputStream

internal fun Path.unpackJarFile(to: Path) {
    if (this.extension != "jar") return

    val zipFile = ZipFile(this.toFile())

    zipFile.entries().asSequence().forEach { entry ->
        if (!entry.name.startsWith("assets/minecraft/models/block")) return@forEach

        val resolved = to.resolve(entry.name).createIfNotExists()
        if (resolved.isDirectory()) return@forEach

        zipFile.getInputStream(entry).copyTo(resolved.outputStream())
    }
}