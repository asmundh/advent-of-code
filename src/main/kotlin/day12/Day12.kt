package day12

import day09.Coordinate
import getEntryAboveOrNull
import getEntryBelowOrNull
import getEntryLeftOrNull
import getEntryRightOrNull
import readInput
import toAocCode
import kotlin.math.abs

data class Vertex(
    val position: Coordinate,
    val cost: Int,
)

data class Edge(
    val cost: Int,
    val leadsTo: Vertex
)

fun Coordinate.heuristic(other: Coordinate) =
    abs(this.x - other.x) + abs(this.y - other.y)

fun Int.isConnected(other: Int) = (this - other) < 2

fun List<List<Int>>.getNeighbors(x: Int, y: Int): List<Pair<Int, Coordinate>> {
    val left = this.getEntryLeftOrNull(x, y)
    val right = this.getEntryRightOrNull(x, y)
    val below = this.getEntryBelowOrNull(x, y)
    val above = this.getEntryAboveOrNull(x, y)
    return listOfNotNull(
        left?.let { it to Coordinate(x - 1, y) },
        right?.let { it to Coordinate(x + 1, y) },
        below?.let { it to Coordinate(x, y + 1) },
        above?.let { it to Coordinate(x, y - 1) },
    ).filter {
        this[y][x].isConnected(it.first)
    }
}

fun List<List<Int>>.aStar(start: Coordinate, goal: Coordinate): Int {
    this.forEach { println(it) }
    val openSet: MutableSet<Vertex> = mutableSetOf(Vertex(start, 0))

    val cameFrom = mutableMapOf<Coordinate, Vertex>()

    val gScore: MutableMap<Coordinate, Int> = mutableMapOf(start to 0)

    val fScore: MutableMap<Coordinate, Int> = mutableMapOf(start to start.heuristic(goal))

    while (openSet.isNotEmpty()) {
        val current = openSet.minBy { it.cost }
        if (current.position == goal)
            return current.cost

        openSet.remove(current)
        val neighbors = this.getNeighbors(current.position.x, current.position.y).filter { it.second != cameFrom[current.position]?.position }
        for (neighbor in neighbors) {
            val tentativeScoreG = gScore[current.position]?.plus(neighbor.first)!!
            val neighborScoreG = gScore[neighbor.second] ?: Int.MAX_VALUE
            if (tentativeScoreG < neighborScoreG) {
                cameFrom[neighbor.second] = current
                gScore[neighbor.second] = tentativeScoreG
                fScore[neighbor.second] = tentativeScoreG  + neighbor.second.heuristic(goal)
                val neighborVertex = Vertex(neighbor.second, fScore[neighbor.second]!!)
                if (!openSet.contains(neighborVertex))
                    openSet.add(neighborVertex)
            }
        }
    }
    throw Exception("FAILURE")
}

fun part1(input: List<String>): Int {
    var startPosition = Coordinate(0, 0)
    var endPosition = Coordinate(0, 0)
    val coordinates: List<MutableList<Int>> = input.mapIndexed { idxY, row ->
        row.mapIndexed inner@{ idxX, it ->
        if (it == 'S') {
            startPosition = Coordinate(idxX, idxY)
            return@inner 'a'.toAocCode()
        }
        if (it == 'E') {
            endPosition = Coordinate(idxX, idxY)
            return@inner 'z'.toAocCode()
        }
        it.toAocCode()
    }.toMutableList()}

    return coordinates.aStar(startPosition, endPosition)
}

fun part2(input: List<String>): Int {
    return 1
}

fun main() {
    val input = readInput("Day12")
    println(part1(input))
//    println(part2(input))
}
