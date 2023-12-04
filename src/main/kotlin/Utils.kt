import datastructures.Coordinate
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.measureTimeMillis

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

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun <T> List<List<T>>.getEntryLeftOrNull(x: Int, y: Int): T? =
    this.getOrNull(y)?.getOrNull(x - 1)

fun <T> List<List<T>>.getEntryRightOrNull(x: Int, y: Int): T? =
    this.getOrNull(y)?.getOrNull(x + 1)

fun <T> List<List<T>>.getEntryAboveOrNull(x: Int, y: Int): T? =
    this.getOrNull(y - 1)?.getOrNull(x)

fun <T> List<List<T>>.getEntryBelowOrNull(x: Int, y: Int): T? =
    this.getOrNull(y + 1)?.getOrNull(x)

/**
 * a = 1, z = 26,
 * A = 27, Z = 52
 */
fun Char.toAocCode(): Int {
    return if (isLowerCase()) {
        code - 96
    } else if (isUpperCase()) {
        code - 38
    } else 0
}

fun String.toHalves(): Pair<String, String> =
    substring(0, length / 2) to substring(length / 2)

fun <T> List<List<T>>.findPositionOf(value: T): Coordinate? {
    this.forEachIndexed { idxY, row ->
        row.forEachIndexed { idxX, entry ->
            if (entry == value) return Coordinate(idxX, idxY)
        }
    }
    return null
}

fun Int.pow(exponent: Int): Int = if (exponent == 0) 1 else this * pow(exponent - 1)

fun runTask(taskName: String, task: () -> Any) {
    var res: Any
    val time = measureTimeMillis {
        res = task()

    }.toDouble()
    println("$taskName - Result: $res, took ${time / 1000} s ($time ms)")
}
