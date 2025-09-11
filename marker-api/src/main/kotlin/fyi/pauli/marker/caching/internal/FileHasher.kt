package fyi.pauli.marker.caching.internal

import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.inputStream

internal fun Path.calculateSHA1Hash(): ByteArray {
    val digest = MessageDigest.getInstance("SHA-1")
    this.inputStream().use { inputStream ->
        var n = 0
        val buffer = ByteArray(8192)
        while (n != -1) {
            n = inputStream.read(buffer)
            if (n > 0) digest.update(buffer, 0, n)
        }
        return digest.digest()
    }
}