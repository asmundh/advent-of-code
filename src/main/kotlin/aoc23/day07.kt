package aoc23

import runTask
import splitToPair
import utils.InputReader
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun day7part1(input: List<String>): Int {
    val cards = input.getCards().sorted()
    return cards.mapIndexed { idx, card ->
        card.bid * (idx + 1)
    }.sum()
}

fun day7part2(input: List<String>): Int {
    val cards = input.getCards(jokerBad = true).sorted()
    return cards.mapIndexed { idx, card ->
        card.bid * (idx + 1)
    }.sum()
}

fun List<String>.getCards(jokerBad: Boolean = false) =
    this.map {
        val (card, bid) = it.splitToPair(' ')
        Hand(card, bid.toInt(), jokerBad)
    }

data class Hand(
    val cards: String,
    val bid: Int,
    val jokerBad: Boolean
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        val otherCardType = other.cards.groupingBy { it }.eachCount().mapToHandType(jokerBad)
        val thisCardType = cards.groupingBy { it }.eachCount().mapToHandType(jokerBad)

        if (thisCardType != otherCardType) return thisCardType - otherCardType

        for (i in 0 until 5) {
            val thisCardScore = this.cards[i].mapToCardNumber(jokerBad)
            val otherCardScore = other.cards[i].mapToCardNumber(jokerBad)

            if (thisCardScore == otherCardScore && i == 4) return 0

            if (thisCardScore != otherCardScore) return thisCardScore - otherCardScore
        }
        throw IllegalStateException("compareTo has not been implemented correctly.")
    }
}

fun Map<Char, Int>.mapToHandType(jokerBad: Boolean = false): Int {
    val amountOfJ = this['J'] ?: 0
    if (amountOfJ == 0 || !jokerBad) return this.mapToScore()

    val enhancedHands: MutableList<Map<Char, Int>> = mutableListOf()
    val handWithoutJ = this.keys.filter { it != 'J' }.associateWith { this[it]!! }

    val scores = mutableListOf<Int>()

    for (i in 0 until handWithoutJ.size) {
        val newHand = handWithoutJ.toMutableMap()
        val cardToInc = newHand.keys.toList()[i]
        newHand[cardToInc] = newHand[cardToInc]?.plus(amountOfJ)!!
        enhancedHands.add(newHand)
        scores.add(newHand.mapToScore())
    }

    return scores.maxOrNull() ?: 7
}

fun Map<Char, Int>.mapToScore(): Int =
    when (this.map { it.value }.sortedDescending().joinToString("")) {
        "5" -> 7
        "41" -> 6
        "32" -> 5
        "311" -> 4
        "221" -> 3
        "2111" -> 2
        "11111" -> 1
        else -> throw IllegalArgumentException("INPUT: $this. Should not happen.")
    }

fun Char.mapToCardNumber(jokerBad: Boolean = false): Int =
    when (this) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> if (jokerBad) 1 else 11
        'T' -> 10
        else -> this.digitToInt()
    }

fun main() {
    val input = InputReader.getInputAsList<String>(7)
    runTask("D7P1") { day7part1(input) }
    runTask("D7P2") { day7part2(input) }
}
