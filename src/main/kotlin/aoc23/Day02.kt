package aoc23

import utils.InputReader
import java.lang.IllegalStateException
import kotlin.math.max

fun day2part1(input: List<String>): Int {
    val games = input.map { parseGame(it) }
    val legalGames = games.filter {
        it.sets.all {
            it.red <= 12 &&
                it.green <= 13 &&
                it.blue <= 14
        }
    }

    return legalGames.sumOf { it.id }
}
fun day2part2(input: List<String>): Int {
    val games = input.map { parseGame(it) }
    return games.sumOf {
        var red = 1
        var blue = 1
        var green = 1

        it.sets.forEach {
            red = max(it.red, red)
            blue = max(it.blue, blue)
            green = max(it.green, green)
        }
        red * blue * green
    }
}

fun parseGame(game: String): Game {
    val idAndSets = game.split(":")
    val id = idAndSets.first().filter { it.isDigit() }.toInt()
    val sets = idAndSets.last().split(";").map {
        val cubes = it.split(",")
        var red = 0
        var blue = 0
        var green = 0
        cubes.forEach { cube ->
            val count = cube.substringBeforeLast(' ').trim().toInt()
            val color = cube.substringAfterLast(' ')
            when (color.length) {
                3 -> red = count
                4 -> blue = count
                5 -> green = count
                else -> throw IllegalStateException("WTF: $cube")
            }
        }
        GameSet(blue, red, green)
    }

    return Game(
        id,
        sets
    )
}

data class Game(
    val id: Int,
    val sets: List<GameSet>
)

data class GameSet(
    val blue: Int,
    val red: Int,
    val green: Int,
)

fun main() {
    val input: List<String> = InputReader.getInputAsList(2)
    println(day2part1(input))
    println(day2part2(input))
}
