package day04

import readInput

fun part1(input: List<String>): Int =
    input.count { inp ->
        inp.split(",").let {
            it[0].surrounds(it[1]) ||
                it[1].surrounds(it[0])
        }
    }

fun part2(input: List<String>): Int =
    input.count { inp ->
        inp.split(",").let {
            it[0].occursIn(it[1]) ||
                it[1].occursIn(it[0])
        }
    }

fun main() {
    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun String.occursIn(other: String): Boolean {
    val firstAssignment = splitToPair()
    val secondAssignment = other.splitToPair()
    return (secondAssignment.first in firstAssignment.first..firstAssignment.second) ||
        (secondAssignment.second in firstAssignment.first..firstAssignment.second) ||
        (firstAssignment.second in secondAssignment.first..secondAssignment.second) ||
        (firstAssignment.first in secondAssignment.first..secondAssignment.second)
}

fun String.surrounds(other: String): Boolean {
    val firstAssignment = splitToPair()
    val secondAssignment = other.splitToPair()
    return (secondAssignment.first in firstAssignment.first..firstAssignment.second) &&
        (secondAssignment.second in firstAssignment.first..firstAssignment.second)
}

fun String.splitToPair(): Pair<Int, Int> {
    val numbers = split("-")
    return numbers[0].toInt() to numbers[1].toInt()
}
