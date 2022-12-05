package day05

import readInput

fun main() {
    val startingCratePart1 = QCrate(
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
    val startingCratePart2 = LCrate(
        stacks = mutableListOf(
            mutableListOf("S", "M", "R", "N", "W", "J", "V", "T"),
            mutableListOf("B", "W", "D", "J", "Q", "P", "C", "V"),
            mutableListOf("B", "J", "F", "H", "D", "R", "P"),
            mutableListOf("F", "R", "P", "B", "M", "N", "D"),
            mutableListOf("H", "V", "R", "P", "T", "B"),
            mutableListOf("C", "B", "P", "T"),
            mutableListOf("B", "J", "R", "P", "L"),
            mutableListOf("N", "C", "S", "L", "T", "Z", "B", "W"),
            mutableListOf("L", "S", "G"),
        )
    )

    fun part1(input: List<String>): String {
        val crate = startingCratePart1.copy()
        input.forEach { ops ->
            val moveOperations = ops
                .filter { it.isDigit() || it.isWhitespace() }
                .split("  ")
                .map { it.replace(" ", "") }
                .map { it.toInt() }
            crate.move(moveOperations[0], moveOperations[1], moveOperations[2])
        }
        return crate.stacks.map { it.last() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val crate = startingCratePart2.copy()
        input.forEach { ops ->
            val moveOperations = ops
                .filter { it.isDigit() || it.isWhitespace() }
                .split("  ")
                .map { it.replace(" ", "") }
                .map { it.toInt() }
            println(moveOperations)
            crate.move(moveOperations[0], moveOperations[1], moveOperations[2])
        }
        return crate.stacks.map { it.last() }.joinToString("")
    }

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

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class QCrate(
    val stacks: MutableList<ArrayDeque<String>>
) {
    fun move(amount: Int, fromPosition: Int, toPosition: Int) {
        val moveFrom = fromPosition - 1
        val moveTo = toPosition - 1
        repeat(amount) {
            val poppedValue = stacks[moveFrom].removeLast()
            stacks[moveTo].addLast(poppedValue)
        }
    }
}
data class LCrate(
    val stacks: MutableList<MutableList<String>>
) {
    fun move(amount: Int, fromPosition: Int, toPosition: Int) {
        val moveFrom = fromPosition - 1
        val moveTo = toPosition - 1
        val poppedValues = stacks[moveFrom].takeLast(amount)
        repeat(amount) {
            stacks[moveFrom].removeLast()
        }
        stacks[moveTo].addAll(poppedValues)
    }
}
