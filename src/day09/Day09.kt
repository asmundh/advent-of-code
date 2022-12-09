package day09

import readInput

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;
    companion object {
        fun from(str: String) = when (str) {
            "U" -> UP
            "D" -> DOWN
            "L" -> LEFT
            "R" -> RIGHT
            else -> throw Exception("Nei nei nei for $str")
        }
    }
}

data class KnottedRope(
    val size: Int,
    val knots: MutableList<Knot> = mutableListOf()
) {
    init {
        knots.add(Knot(name = "H", parentKnot = null))
        for (i in 1 until size) {
            knots.add(Knot(name = "$i", parentKnot = knots.last()))
        }
    }
    fun move(direction: Direction) {
        for (knot in knots) {
            if (knot.parentKnot == null) knot.move(direction)
            else knot.follow(knot.parentKnot)
        }
    }
}

data class Knot(
    var position: Coordinate = Coordinate(0, 0),
    val name: String,
    val parentKnot: Knot?,
) {
    fun move(direction: Direction) {
        position = when (direction) {
            Direction.UP -> position.copy(y = position.y + 1)
            Direction.DOWN -> position.copy(y = position.y - 1)
            Direction.LEFT -> position.copy(x = position.x - 1)
            Direction.RIGHT -> position.copy(x = position.x + 1)
        }
    }

    fun follow(parentKnot: Knot?) {
        if (parentKnot == null || position.touches(parentKnot.position)) return

        val moveX = parentKnot.position.x.compareTo(position.x)
        val moveY = parentKnot.position.y.compareTo(position.y)
        position = position.copy(x = position.x + moveX, y = position.y + moveY)
    }
    override fun toString() = "${position.x},${position.y}($name)"
}

data class Coordinate(
    var x: Int,
    var y: Int
) {
    private fun touchesX(other: Coordinate): Boolean = (this.x == other.x) ||
        (this.x == (other.x - 1)) ||
        (this.x == (other.x + 1))

    private fun touchesY(other: Coordinate): Boolean =
        (this.y == other.y) ||
            (this.y == (other.y - 1)) ||
            (this.y == (other.y + 1))

    fun touches(other: Coordinate): Boolean = touchesX(other) && touchesY(other)
}

fun part1(input: List<String>): Int {
    val visitedCells: MutableSet<Coordinate> = mutableSetOf()
    val rope = KnottedRope(2)
    input.forEach {
        val command = it.split(" ")
        repeat(command[1].toInt()) {
            rope.move(Direction.from(command[0]))
            visitedCells.add(rope.knots.last().position)
        }
    }
    return visitedCells.size
}

fun part2(input: List<String>): Int {
    val visitedCells: MutableSet<Coordinate> = mutableSetOf()
    val rope = KnottedRope(size = 10)
    input.forEach {
        val command = it.split(" ")
        repeat(command[1].toInt()) {
            rope.move(Direction.from(command[0]))
            visitedCells.add(rope.knots.last().position)
        }
    }
    return visitedCells.size
}

fun main() {
    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
