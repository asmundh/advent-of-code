package aoc22

import readInput

val startingStack = """
    [T] [V]                     [W]
    [V] [C] [P] [D]             [B]
    [J] [P] [R] [N] [B]         [Z]
    [W] [Q] [D] [M] [T]     [L] [T]
    [N] [J] [H] [B] [P] [T] [P] [L]
    [R] [D] [F] [P] [R] [P] [R] [S] [G]
    [M] [W] [J] [R] [V] [B] [J] [C] [S]
    [S] [B] [B] [F] [H] [C] [B] [N] [L]
    1   2   3   4   5   6   7   8   9
"""

fun main() {
    val startingCratePart1 = Crate(
        stacks = mutableListOf(
            ArrayDeque(listOf("S", "M", "R", "N", "W", "J", "V", "T")),
            ArrayDeque(listOf("B", "W", "D", "J", "Q", "P", "C", "V")),
            ArrayDeque(listOf("B", "J", "F", "H", "D", "R", "P")),
            ArrayDeque(listOf("F", "R", "P", "B", "M", "N", "D")),
            ArrayDeque(listOf("H", "V", "R", "P", "T", "B")),
            ArrayDeque(listOf("C", "B", "P", "T")),
            ArrayDeque(listOf("B", "J", "R", "P", "L")),
            ArrayDeque(listOf("N", "C", "S", "L", "T", "Z", "B", "W")),
            ArrayDeque(listOf("L", "S", "G")),
        )
    )

    fun part1(input: List<String>): String {
        val crate = Crate(
            stacks = startingCratePart1.stacks.map { it -> ArrayDeque(it.map { it }) }.toMutableList()
        )
        input.forEach { ops ->
            val moveOperations = ops.toOperations()
            crate.moveOneAtATime(moveOperations[0], moveOperations[1], moveOperations[2])
        }
        return crate.stacks.joinToString("") { it.last() }
    }

    fun part2(input: List<String>): String {
        val crate = Crate(
            stacks = startingCratePart1.stacks.map { it -> ArrayDeque(it.map { it }) }.toMutableList()
        )
        input.forEach { ops ->
            val moveOperations = ops.toOperations()
            crate.moveMultiple(moveOperations[0], moveOperations[1], moveOperations[2])
        }
        return crate.stacks.joinToString("") { it.last() }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun String.toOperations() =
    filter { it.isDigit() || it.isWhitespace() }
        .split("  ")
        .map { it.replace(" ", "") }
        .map { it.toInt() }

data class Crate(
    val stacks: MutableList<ArrayDeque<String>>
) {
    fun moveOneAtATime(amount: Int, fromPosition: Int, toPosition: Int) {
        val moveFrom = fromPosition - 1
        val moveTo = toPosition - 1
        repeat(amount) {
            val poppedValue = stacks[moveFrom].removeLast()
            stacks[moveTo].addLast(poppedValue)
        }
    }

    fun moveMultiple(amount: Int, fromPosition: Int, toPosition: Int) {
        val moveFrom = fromPosition - 1
        val moveTo = toPosition - 1
        val toMove = ArrayDeque<String>()
        repeat(amount) {
            toMove.addFirst(stacks[moveFrom].removeLast())
        }
        stacks[moveTo].addAll(toMove)
    }
}
