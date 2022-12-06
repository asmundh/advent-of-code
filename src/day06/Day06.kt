package day06

import readFirstLine

fun String.indexOfFirstWhollyUniqueWindowOfSize(size: Int) =
    windowed(size, 1)
        .indexOfFirst { it.toSet().size == size } + size

fun main() {
    fun part1(input: String): Int = input
        .indexOfFirstWhollyUniqueWindowOfSize(4)

    fun part2(input: String): Int = input
        .indexOfFirstWhollyUniqueWindowOfSize(14)

    val input = readFirstLine("Day06")
    println(part1(input))
    println(part2(input))
}
