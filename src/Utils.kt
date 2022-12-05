import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.time.ExperimentalTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/${name.lowercase()}", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Prints out
 */
@OptIn(ExperimentalContracts::class, ExperimentalTime::class)
inline fun printlnTimed(block: () -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
}
