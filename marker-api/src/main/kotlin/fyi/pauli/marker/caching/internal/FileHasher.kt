package fyi.pauli.marker.caching.internal

import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.security.MessageDigest

internal fun Path.calculateSHA1Hash(): ByteArray {
    val digest = MessageDigest.getInstance("SHA-1")

    FileChannel.open(this, StandardOpenOption.READ).use { channel ->
        val buffer = ByteBuffer.allocate(8192)
        while (channel.read(buffer) != -1) {
            buffer.flip()
            digest.update(buffer)
            buffer.clear()
        }

        return digest.digest()
    }
}