package aoc22

import datastructures.Direction
import readInput


fun List<List<Int>>.heightOfTreeInDirection(direction: Direction, y: Int, x: Int): Int {
    if ((y == 0) || (x == 0) || (y == lastIndex) || x == lastIndex) return -1

    return when (direction) {
        Direction.NORTH -> this[y - 1][x]
        Direction.WEST -> this[y][x - 1]
        Direction.EAST -> this[y][x + 1]
        Direction.SOUTH -> this[y + 1][x]
    }
}

fun List<List<Int>>.isVisibleInDirection(direction: Direction, y: Int, x: Int, currentTree: Int): Boolean {
    val treeToCheck = heightOfTreeInDirection(direction, y, x)
    return ((treeToCheck == -1) || currentTree > treeToCheck)
}

fun List<List<Int>>.isVisibleFromAbove(y: Int, x: Int): Boolean {
    for (idx in y downTo 1 step 1) {
        val isVisible = isVisibleInDirection(Direction.NORTH, idx, x, this[y][x])
        if (!isVisible) return false
    }
    return true
}

fun List<List<Int>>.isVisibleFromBelow(y: Int, x: Int): Boolean {
    for (idx in y until this.size) {
        val isVisible = isVisibleInDirection(Direction.SOUTH, idx, x, this[y][x])
        if (!isVisible) return false
    }
    return true
}

fun List<List<Int>>.isVisibleFromRight(y: Int, x: Int): Boolean {
    for (idx in x until this[y].size) {
        val isVisible = isVisibleInDirection(Direction.EAST, y, idx, this[y][x])
        if (!isVisible) return false
    }
    return true
}

fun List<List<Int>>.isVisibleFromLeft(y: Int, x: Int): Boolean {
    for (idx in x downTo 0 step 1) {
        val isVisible = isVisibleInDirection(Direction.WEST, y, idx, this[y][x])
        if (!isVisible) return false
    }
    return true
}

fun String.splitToIntList(): List<Int> =
    this.map { it.toString().toInt() }

fun day8part1(input: List<String>): Int {
    val treeLines = input.map { it.splitToIntList() }
    var count = 0
    treeLines.forEachIndexed { y, trees ->
        trees.forEachIndexed { x, _ ->
            val bools = listOf(
                treeLines.isVisibleFromAbove(y, x) ||
                    treeLines.isVisibleFromBelow(y, x) ||
                    treeLines.isVisibleFromLeft(y, x) ||
                    treeLines.isVisibleFromRight(y, x)
            )
            if (bools.any { it }) count += 1
        }
    }
    return count
}

fun day8part2(input: List<String>): Int {
    val sum = input.sumOf { it.length }
    return sum
}

fun main() {
    val input = readInput("Day08")
    println(day8part1(input))
    println(day8part2(input))
}
