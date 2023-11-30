package aoc22

import readFirstLine

fun String.indexOfFirstWhollyUniqueWindowOfSize(size: Int) =
    windowed(size, 1)
        .indexOfFirst { it.toSet().size == size } + size

fun day6part1(input: String): Int = input
    .indexOfFirstWhollyUniqueWindowOfSize(4)

fun day6part2(input: String): Int = input
    .indexOfFirstWhollyUniqueWindowOfSize(14)

fun main() {

    val input = readFirstLine("Day06")
    println(day6part1(input))
    println(day6part2(input))
}
