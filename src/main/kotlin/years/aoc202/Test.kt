package years.aoc202

fun part2(input: List<String>): Int {
    val maxSums: MutableList<Int> = mutableListOf()
    var newSum = 0
    input.forEach {
        if (it.isBlank()) {
            maxSums.add(newSum)
            newSum = 0
            return@forEach
        }
        newSum += it.toInt()
    }
    maxSums.sort()
    return maxSums.takeLast(3).sum()
}
