package day02

import readInput

fun main() {
    fun getScore(yourPick: Hands, opponentPick: Hands): Int {
        val roundScore = when (yourPick) {
            Hands.ROCK -> when (opponentPick) {
                Hands.ROCK -> Scores.DRAW
                Hands.SCISSOR -> Scores.WIN
                Hands.PAPER -> Scores.LOSS
            }
            Hands.PAPER -> when (opponentPick) {
                Hands.ROCK -> Scores.WIN
                Hands.SCISSOR -> Scores.LOSS
                Hands.PAPER -> Scores.DRAW
            }
            Hands.SCISSOR -> when (opponentPick) {
                Hands.ROCK -> Scores.LOSS
                Hands.PAPER -> Scores.WIN
                Hands.SCISSOR -> Scores.DRAW
            }
        }
        return roundScore.score + yourPick.score
    }

    fun part1(input: List<String>): Int =
        input.sumOf {
            val hands = it.split(" ")
            getScore(Hands.from(hands[1]), Hands.from(hands[0]))
        }

    fun part2(input: List<String>): Int = input.sumOf {
        val strategy = it.split(" ")
        val opponentsHand = Hands.from(strategy[0])
        getScore(
            opponentsHand.getHandForStrategy(Strategy.from(strategy[1])),
            opponentsHand
        )
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Hands(
    val score: Int,
    private val losesTo: String,
    private val winsOver: String
) {
    ROCK(1, "PAPER", "SCISSOR"),
    PAPER(2, "SCISSOR", "ROCK"),
    SCISSOR(3, "ROCK", "PAPER");

    fun getHandForStrategy(strategy: Strategy): Hands =
        when (strategy) {
            Strategy.WIN -> Hands.valueOf(losesTo)
            Strategy.DRAW -> this
            Strategy.LOSE -> Hands.valueOf(winsOver)
        }

    companion object {
        fun from(shortName: String): Hands =
            when (shortName) {
                "X", "A" -> ROCK
                "Y", "B" -> PAPER
                "Z", "C" -> SCISSOR
                else -> throw Exception("Nei nei nei: $shortName")
            }
    }
}

enum class Strategy() {
    LOSE,
    WIN,
    DRAW;

    companion object {
        fun from(shortHand: String): Strategy = when (shortHand) {
            "X" -> LOSE
            "Y" -> DRAW
            "Z" -> WIN
            else -> throw Exception("Huff da")
        }
    }
}

enum class Scores(val score: Int) {
    DRAW(3),
    LOSS(0),
    WIN(6);
}
