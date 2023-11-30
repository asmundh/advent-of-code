package utils
import java.io.File

object InputReader {

    fun getInputAsString(day: Int): String =
        fromResources(day = day).readText()

    inline fun <reified T> getInputAsList(day: Int): List<T> =
        fromResources(day = day).readLines().map { it as T }

    inline fun <reified T> getInputListSeparatedByBlankLines(day: Int): List<List<T>> =
        getInputAsString(day)
            .split("\n\n")
            .map {
                it.lines().mapNotNull { line ->
                    if (line.isEmpty()) {
                        return@mapNotNull null
                    }
                    when (T::class) {
                        Int::class -> line.toInt() as T
                        Double::class -> line.toDouble() as T
                        String::class -> line as T
                        else -> throw NotImplementedError("This mapping has not been implemented")
                    }
                }
            }

    fun fromResources(year: Int = 2023, day: Int): File {
        return File(javaClass.getResource("/$year/day$day.txt")!!.toURI())
    }
}
