package aoc23

import runTask
import splitToPair
import utils.InputReader
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.math.max

//fun day7part1(input: List<String>): Long {
//    val cards = input.map {
//        val (card, bid) = it.splitToPair(' ')
//        return@map Card(
//            card.sortedAsCard(),
//            card,
//            bid.toLong(),
//        )
//    }.sorted()
//    return cards.mapIndexed { idx, card ->
//        card.bid * (idx + 1)
//    }.sum()
//}

fun day7part2(input: List<String>): Long {
    val cards = input.map {
        val (card, bid) = it.splitToPair(' ')
        return@map Card(
            card.sortedAsCard(),
            card,
            bid.toLong(),
        )
    }.sorted()
    return cards.mapIndexed { idx, card ->
        card.bid * (idx + 1)
    }.sum()
}

fun String.sortedAsCard(): String {
    val letters = filter { it.isLetter() }
    val aces = letters.filter { it == 'A' }
    val kings = letters.filter { it == 'K' }
    val queens = letters.filter { it == 'Q' }
    val knights = letters.filter { it == 'J' }
    val tens = letters.filter { it == 'T' }
    val nums = filter { it.isDigit() }.map { it.digitToInt() }.sortedDescending().joinToString("")
    return aces + kings + queens + knights + tens + nums
}

data class Card(
    val card: String,
    val originalCard: String,
    val bid: Long,
): Comparable<Card> {
    override fun compareTo(other: Card): Int {
        val thisCardAndCounts: MutableMap<Char, Long> = mutableMapOf()
        val otherCardAndCounts: MutableMap<Char, Long> = mutableMapOf()
        card.forEach {
            thisCardAndCounts[it] = max(thisCardAndCounts[it]?.plus(1) ?: -1, 1)
        }
        other.card.forEach {
            otherCardAndCounts[it] = max(otherCardAndCounts[it]?.plus(1) ?: -1, 1)
        }

        val thisCardType = thisCardAndCounts.mapToHandType()
        val otherCardType = otherCardAndCounts.mapToHandType()

        if (thisCardType > otherCardType) {
            return 1 // Return this
        } else if (thisCardType < otherCardType) {
            return -1 // return other
        } else { // equal type
            for (i in 0 until 5) {
                val thisCard = this.originalCard[i]
                val thisCardScore = thisCard.mapToCardNumber()

                val otherCard = other.originalCard[i]
                val otherCardScore = otherCard.mapToCardNumber()

                if (thisCardScore > otherCardScore) {
                    return 1
                } else if (thisCardScore < otherCardScore) {
                    return -1
                } else {
                    if (i == 4) return 0
                }
            }
        }

        throw IllegalStateException("WTF $this VS $other")
    }
}

fun MutableMap<Char, Long>.mapToHandType() : Long {
    return when (map {it.value}.sortedDescending().joinToString("")) {
        "5" -> 7
        "41" -> 6
        "32" -> 5
        "311" -> 4
        "221" -> 3
        "2111" -> 2
        "11111" -> 1
        else -> throw IllegalArgumentException("WHAT IS THIS: ${map {it.value}.sortedDescending().joinToString("")}")
    }
}

fun Char.mapToCardNumber(): Long =
    when (this) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        else -> this.digitToInt().toLong()
    }

fun main() {
    val input = InputReader.getInputAsList<String>(7)
    runTask("D7P1") { day7part1(input) }
    runTask("D7P2") { day7part2(input) }
}
