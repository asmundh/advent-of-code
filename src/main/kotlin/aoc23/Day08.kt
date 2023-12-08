package aoc23

import lcm
import lcmOfList
import runTask
import utils.InputReader
import java.lang.IllegalStateException
import kotlin.collections.Map

fun day8part1(input: List<String>): Int {
    val m = input.toSwampMap()
    var current = "AAA"
    var index = 0
    var moves = 0
    while (current != "ZZZ") {
        current = when (m.instructions[index++]) {
            'L' -> m.maps[current]?.left!!
            'R' -> m.maps[current]?.right!!
            else -> throw IllegalStateException("What the hell")
        }
        index %= m.instructions.length
        moves ++
    }
    return moves
}

fun day8part2(input: List<String>): Long {
    val swampMap = input.toSwampMap()
    val locations = swampMap.maps.keys.filter { it.last() == 'A' }
    var index = 0

    val goals = mutableListOf<Long>()

    locations.forEach {
        var current = it
        var moves = 0L
        while (current.last() != 'Z') {
            current = when (swampMap.instructions[index++]) {
                'L' -> swampMap.maps[current]?.left!!
                'R' -> swampMap.maps[current]?.right!!
                else -> throw IllegalStateException("What the hell")
            }
            index %= swampMap.instructions.length
            moves ++
        }
        goals.add(moves)
    }
    return lcmOfList(goals)
}

data class SwampMap(
    val instructions: String,
    val maps: Map<String, Target>
)

data class Target(
    val left: String,
    val right: String,
)

fun List<String>.toSwampMap(): SwampMap =
    SwampMap(
        this.first(),
        this.drop(2).associate {
            val target = it.substring(0, 3)
            val left = it.substring(7, 10)
            val right = it.substring(12, 15)

            target to Target(left, right)
        }
    )

fun main() {
    val input = InputReader.getInputAsList<String>(8)
//    runTask("D8P1") { day8part1(input) }
    runTask("D8P2") { day8part2(input) }
}
