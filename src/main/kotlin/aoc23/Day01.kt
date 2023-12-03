package aoc23

import utils.InputReader

fun day1part1(input: List<String>): Int {
    return input.sumOf {
        val d1 = it.first { it.isDigit() }.digitToInt()
        val d2 = it.last() { it.isDigit() }.digitToInt()
        "$d1$d2".toInt()
    }
}

fun day1part2(input: List<String>): Int {
    return input.sumOf {
        val first: Int = findFirstDigitInString(it)
        val second: Int = findFirstDigitInString(it.reversed(), true)
        "${first}$second".toInt()
    }
}

fun findFirstDigitInString(input: String, isReversed: Boolean = false): Int {
    val candidates: MutableMap<Int, Int> = mutableMapOf()
    val firstIntDigit = input.map { it }.indexOfFirst { it.isDigit() }
    if (firstIntDigit != -1) candidates[firstIntDigit] = input[firstIntDigit].digitToInt()
    if (input.length >= 3) {
        val window = input.windowed(3, 1).map { if (isReversed) it.reversed() else it }
        val firstThreeIndex = window.indexOfFirst { getDigit(it) != -1 }
        if (firstThreeIndex != -1) candidates[firstThreeIndex] = getDigit(window[firstThreeIndex])
    }
    if (input.length >= 4) {
        val window = input.windowed(4, 1).map { if (isReversed) it.reversed() else it }
        val firstFourIndex = input.windowed(4, 1).map { if (isReversed) it.reversed() else it }.indexOfFirst { getDigit(it) != -1 }
        if (firstFourIndex != -1) candidates[firstFourIndex] = getDigit(window[firstFourIndex])
    }
    if (input.length >= 5) {
        val window = input.windowed(5, 1).map { if (isReversed) it.reversed() else it }
        val firstFiveIndex = input.windowed(5, 1).map { if (isReversed) it.reversed() else it }.indexOfFirst { getDigit(it) != -1 }
        if (firstFiveIndex != -1)candidates[firstFiveIndex] = getDigit(window[firstFiveIndex])
    }
    return candidates[candidates.keys.min()]!!
}

fun getDigit(input: String): Int {
    val letters3 = mapOf("one" to 1, "two" to 2, "six" to 6)
    val letters4 = mapOf("four" to 4, "five" to 5, "nine" to 9)
    val letters5 = mapOf("three" to 3, "seven" to 7, "eight" to 8)
    return when (input.length) {
        1 -> if (input.first().isDigit()) input.first().digitToInt() else -1
        3 -> letters3[input] ?: -1
        4 -> letters4[input] ?: -1
        5 -> letters5[input] ?: -1
        else -> -1
    }
}

fun main() {
    val input: List<String> = InputReader.getInputAsList(1)
    println(day1part1(input))
    println(day1part2(input))
}
