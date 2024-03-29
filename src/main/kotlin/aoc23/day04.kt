package aoc23

import getIntegerListsSplitBy
import pow
import runTask
import toOnlyInteger
import toSets
import utils.InputReader
import kotlin.math.max

fun day4part1(input: List<String>): Int =
    input.sumOf {
        getScratchCard(it).getScore()
    }

fun day4part2(input: List<String>): Int {
    val scratchCards = input.map { getScratchCard(it) }.associateBy { it.id }
    val counts: MutableMap<Int, Int> = mutableMapOf()

    val backlog: MutableList<ScratchCard> = scratchCards.values.toMutableList()

    var index = 0
    while (index < backlog.size) {
        val card = backlog[index++]
        counts[card.id] = max((counts[card.id]?.plus(1)) ?: -1, 1)
        for (i in 1..card.amountOfWinningCards) {
            scratchCards[card.id + i]?.let { newCard ->
                backlog.add(newCard)
            }
        }
    }
    return counts.values.sum()
}

fun getScratchCard(card: String): ScratchCard {
    val (winningNumbers, scratchedNumbers) = card.substringAfter(':').getIntegerListsSplitBy('|').toSets()
    return ScratchCard(
        card.substringBefore(":").toOnlyInteger(),
        winningNumbers,
        scratchedNumbers,
        winningNumbers.intersect(scratchedNumbers).size
    )
}

data class ScratchCard(
    val id: Int,
    val winningNumbers: Set<Int>,
    val scratchedNumbers: Set<Int>,
    val amountOfWinningCards: Int,
) {
    fun getScore(): Int {
        val wins = scratchedNumbers.filter { winningNumbers.contains(it) }.size
        return if (wins < 2) wins else 2.pow(wins)
    }
}

fun main() {
    val input: List<String> = InputReader.getInputAsList(4)
    runTask("D4p1") { day4part1(input) }
    runTask("D4p2") { day4part2(input) }
}
