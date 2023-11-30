package aoc22

import readInput
import toAocCode
import toHalves


fun day3part1(input: List<String>): Int =
    input.sumOf {
        val halves = it.toHalves()
        halves.first
            .toSet()
            .intersect(halves.second.toSet())
            .first()
            .toAocCode()
    }

fun day3part2(input: List<String>): Int =
    input.chunked(3).sumOf {
        val first = it.first().toSet()
        val second = it[1].toSet()
        val third = it.last().toSet()
        val badge = (first.intersect(second)).intersect(third).first()
        badge.toAocCode()
    }

fun main() {

    val input = readInput("Day03")
    println(day3part1(input))
    println(day3part2(input))
}
