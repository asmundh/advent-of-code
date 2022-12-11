import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources/", "$name.txt")
    .readLines()

fun readFirstLine(name: String) = File("src/main/resources/", "$name.txt")
    .readLines().first()
/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun String.splitToPair(): Pair<String, String> =
    substring(0, length / 2) to substring(length / 2)
