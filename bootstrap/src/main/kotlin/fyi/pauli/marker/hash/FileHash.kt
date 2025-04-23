package fyi.pauli.marker.hash

import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.inputStream

fun calculateSha1Hash(file: Path): ByteArray {
    val digest = MessageDigest.getInstance("SHA-1")
    val input = file.inputStream()

    var n = 0
    val buffer = ByteArray(8192)

    while (n != -1) {
        n = input.read(buffer)
        if (n > 0) digest.update(buffer, 0, n)
    }

    return digest.digest()
}