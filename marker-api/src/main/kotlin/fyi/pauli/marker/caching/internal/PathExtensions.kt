package fyi.pauli.marker.caching.internal

import java.nio.file.Path
import kotlin.io.path.*

internal fun Path.createIfNotExists(create: Path.() -> Unit = {}): Path = this.apply {
    if (parent.notExists()) parent.createDirectories()
    if (exists()) return@apply
    if (isDirectory()) createDirectory() else createFile()
    create()
}