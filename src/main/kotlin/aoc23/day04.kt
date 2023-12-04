package aoc23

import utils.InputReader
import kotlin.math.max
import kotlin.time.ExperimentalTime

fun day4part1(input: List<String>): Int {
    return input.sumOf {
        getScratchCard(it).getScore()
    }
}

fun day4part2(input: List<String>): Int {
    val scratchCards = input.map { getScratchCard(it) }.associateBy { it.id }
    val counts: MutableMap<Int, Int> = mutableMapOf()

    val backlog: MutableList<ScratchCard> = scratchCards.values.toMutableList()
    val cache: MutableMap<Int, Int> = mutableMapOf()

    var index = 0
    while (index < backlog.size) {
        val card = backlog[index++]
        val wins = cache[card.id] ?: card.getAmountOfWinningCards().also { cache[card.id] = it }
        counts[card.id] = max((counts[card.id]?.plus(1)) ?: -1, 1)
        for (i in 1 .. wins) {
            val newCard = scratchCards[card.id + i]
            if (newCard != null) {
                backlog.add(newCard)
            }
        }
    }
    return counts.values.sum()
}

fun getScratchCard(card: String): ScratchCard {
    val cardWithNumbers = card.split(":")
    val winnersAndScratches = cardWithNumbers[1].split("|")
    val winners = winnersAndScratches.first().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    val scratches = winnersAndScratches[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    return ScratchCard(
        cardWithNumbers.first().takeLastWhile { it.isDigit() }.toInt(),
        winners,
        scratches
    )
}

data class ScratchCard(
    val id: Int,
    val winningNumbers: List<Int>,
    val scratchedNumbers: List<Int>
) {
    fun getScore(): Int {
        val wins = scratchedNumbers.filter { winningNumbers.contains(it) }.size
        if (wins == 0) return 0
        if (wins == 1) return 1
        var score = 1
        repeat(wins - 1) {
            score *= 2
        }
        return score
    }

    fun getAmountOfWinningCards(): Int = scratchedNumbers.filter { winningNumbers.contains(it) }.size
}

fun main() {
    val input: List<String> = InputReader.getInputAsList(4)
    println(day4part1(input))
    println(day4part2(input))
}
