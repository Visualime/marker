package fyi.pauli.marker.unpack

import java.nio.file.Path
import java.util.zip.ZipFile
import kotlin.io.path.createDirectory
import kotlin.io.path.createParentDirectories
import kotlin.io.path.notExists
import kotlin.io.path.outputStream

fun unpackJarFile(jarFile: Path, destinationFolder: Path) {
    with(ZipFile(jarFile.toFile())) {
        entries().asSequence().forEach { entry ->
            if (!entry.name.startsWith("assets/minecraft/models/block")) return@forEach
            println(entry.name)
            destinationFolder.resolve(entry.name).apply {
                if (parent.notExists()) createParentDirectories()
                if (entry.isDirectory) {
                    if (notExists()) createDirectory()
                } else {
                    getInputStream(entry).copyTo(outputStream())
                }
            }
        }
    }
}