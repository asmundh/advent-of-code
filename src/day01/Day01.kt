package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var maxSum = 0
        var newSum = 0
        input.forEach {
            if (it.isBlank()) {
                maxSum = maxOf(maxSum, newSum)
                newSum = 0
                return@forEach
            }
            newSum += it.toInt()
        }
        return maxSum
    }

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

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
