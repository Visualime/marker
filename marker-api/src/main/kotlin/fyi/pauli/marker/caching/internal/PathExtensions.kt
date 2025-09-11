package fyi.pauli.marker.caching.internal

import java.nio.file.Path
import kotlin.io.path.*

internal fun Path.createIfNotExists(directory: Boolean = false, create: Path.() -> Unit = {}): Path = this.apply {
    if (parent.notExists()) parent.createDirectories()
    if (exists()) return@apply
    if (directory) createDirectory() else createFile()
    create()
}