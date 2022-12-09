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

data class Rope(
    val head: Head,
    val tail: Tail
) {
    fun move(direction: Direction) {
        head.move(direction)
        tail.follow(head, direction)
    }
}

abstract class RopePart(
    var position: Coordinate
)

data class Head(
    val name: String = "HEAD"
): RopePart(
    position = Coordinate(0, 0),
) {
    fun move(direction: Direction) {
        position = when (direction) {
            Direction.UP -> position.copy(y = position.y + 1)
            Direction.DOWN -> position.copy(y = position.y - 1)
            Direction.LEFT -> position.copy(x = position.x - 1)
            Direction.RIGHT -> position.copy(x = position.x + 1)
        }
    }
    override fun toString() = "${position.x},${position.y}"
}

data class Tail(
    val name: String = "HEAD"
): RopePart(
    position = Coordinate(0, 0),
) {
    fun follow(head: RopePart, direction: Direction) {
        if (position.touches(head.position)) return
        else {
            position = when (direction) {
                Direction.UP -> head.position.copy(y = head.position.y - 1)
                Direction.DOWN -> head.position.copy(y = head.position.y + 1)
                Direction.LEFT -> head.position.copy(x = head.position.x + 1)
                Direction.RIGHT -> head.position.copy(x = head.position.x - 1)
            }
        }
    }
    override fun toString() = "${position.x},${position.y}"
}

data class Cell(
    val visited: Boolean = false,
    val visitedBy: Set<RopePart>,
)

data class Coordinate(
    var x: Int,
    var y: Int
) {
    fun touches(other: Coordinate): Boolean {
        val touchesX =
            (this.x == other.x) ||
                (this.x == (other.x - 1)) ||
                (this.x == (other.x + 1))

        val touchesY =
            (this.y == other.y) ||
                (this.y == (other.y - 1)) ||
                (this.y == (other.y + 1))

        return touchesX && touchesY
    }
}

data class Board(
    val center: Coordinate = Coordinate(0, 0),
    val cells: MutableMap<Coordinate, Cell> = mutableMapOf()
)

fun part1(input: List<String>): Int {
    val board = Board()
    val visitedCells: MutableMap<String, Boolean> = mutableMapOf()
    val rope = Rope(head = Head(), tail = Tail())
    visitedCells[rope.tail.toString()] = true
    input.forEach {
        val command = it.split(" ")
        repeat(command[1].toInt()) {
            rope.move(Direction.from(command[0]))
            println(rope)
            visitedCells[rope.tail.toString()] = true
        }
    }
    println(visitedCells)
    return visitedCells.size
}

fun main() {

    fun part2(input: List<String>): Int {
        return 1
    }

    val input = readInput("Day09")
//    val coord1 = Coordinate(0, 0)
//    println(coord1.touches(coord1.copy(x = 0, y = 0,)))
//    println(coord1.touches(coord1.copy(x = 1, y = -1,)))
//    println(coord1.touches(coord1.copy(x = -1, y = 1,)))
//    println(coord1.touches(coord1.copy(x = -1, y = -1,)))
//    println(coord1.touches(coord1.copy(x = 1, y = 1,)))

    println(part1(input))
//    println(part2(input))
}
