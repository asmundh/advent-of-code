package aoc22

import datastructures.Coordinate
import getEntryAboveOrNull
import getEntryBelowOrNull
import getEntryLeftOrNull
import getEntryRightOrNull
import readInput
import toAocCode
import java.util.PriorityQueue
import kotlin.math.abs

data class Vertex(
    val position: Coordinate,
    val fScore: Int,
)

data class Grid(
    val startPosition: Coordinate,
    val endPosition: Coordinate,
    val grid: List<List<Int>>,
    val lowValues: List<Coordinate>
)

fun List<String>.toGrid(): Grid {
    var startPosition = Coordinate(0, 0)
    var endPosition = Coordinate(0, 0)
    val lowValues = mutableListOf<Coordinate>()
    val grid = this.mapIndexed { idxY, row ->
        row.mapIndexed inner@{ idxX, it ->
            if (it == 'S') {
                startPosition = Coordinate(idxX, idxY)
                lowValues.add(startPosition)
                return@inner 'a'.toAocCode()
            }
            if (it == 'E') {
                endPosition = Coordinate(idxX, idxY)
                return@inner 'z'.toAocCode()
            }
            if (it == 'a') lowValues.add(Coordinate(idxX, idxY))
            it.toAocCode()
        }.toMutableList()
    }
    return Grid(startPosition, endPosition, grid, lowValues)
}

fun reconstructPath(cameFrom: MutableMap<Coordinate, Vertex>, current: Vertex): Int {
    var total = 0
    var current2 = current
    while (cameFrom.keys.contains(current2.position)) {
        current2 = cameFrom[current2.position]!!
        total += 1
    }
    return total
}

fun Coordinate.heuristic(other: Coordinate) =
    abs(this.x - other.x) + abs(this.y - other.y)

fun Int.isConnected(other: Int) = other - this < 2

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
    val openSet = PriorityQueue<Vertex> { a, b -> a.fScore - b.fScore }
    openSet.add(Vertex(start, 0))

    val cameFrom = mutableMapOf<Coordinate, Vertex>()

    val gScore: MutableMap<Coordinate, Int> = mutableMapOf(start to 0)

    val fScore: MutableMap<Coordinate, Int> = mutableMapOf(start to start.heuristic(goal))

    while (openSet.isNotEmpty()) {
        val current = openSet.poll()
        if (current.position == goal)
            return reconstructPath(cameFrom, current)

        openSet.remove(current)
        val neighbors = this.getNeighbors(current.position.x, current.position.y) // .filter { it.second != cameFrom[current.position]?.position }
        for (neighbor in neighbors) {
            val neighborPosition = neighbor.second
            val currentG = gScore[current.position]
            val edgeWeight = 1
            val tentativeScoreG = currentG!!.plus(edgeWeight)
            val neighborScoreG = gScore[neighborPosition] ?: Int.MAX_VALUE

            if (tentativeScoreG < (neighborScoreG)) {
                cameFrom[neighborPosition] = current
                gScore[neighborPosition] = tentativeScoreG
                fScore[neighborPosition] = tentativeScoreG + neighborPosition.heuristic(goal)
                val neighborVertex = Vertex(neighborPosition, fScore[neighborPosition]!!)
                if (!openSet.contains(neighborVertex))
                    openSet.add(neighborVertex)
            }
        }
    }
    return Int.MAX_VALUE
}

fun day12part1(input: List<String>): Int {
    val grid = input.toGrid()
    return grid.grid.aStar(grid.startPosition, grid.endPosition)
}

fun day12part2(input: List<String>): Int {
    val grid = input.toGrid()
    val attempts = mutableListOf<Int>()
    println(grid.lowValues)
    for (startPosition in grid.lowValues) {
        val cost = grid.grid.aStar(startPosition, grid.endPosition)
        attempts.add(cost)
    }
    return attempts.min()
}

fun main() {
    val input = readInput("Day12")

    println(day12part1(input))

    println(day12part2(input))
}
