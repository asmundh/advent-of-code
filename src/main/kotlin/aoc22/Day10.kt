package aoc22

import readInput

fun Map<Int, Int>.getSignalStrengthAtCycles(cycles: List<Int>): Int {
    var sum = 0
    for (cycle in cycles) {
        sum += this[cycle]!! * cycle
    }
    return sum
}

data class Sprite(
    var position: Int = 1
) {
    fun move(steps: Int) = run {
        position += steps
    }

    fun pixelToRender(cycle: Int): Char {
        val pixelPosition = (cycle - 1)
        return if (pixelPosition in position - 1..position + 1) '#' else '.'
    }
}

data class CRT(
    val size: Int = 40,
    val sprite: Sprite
) {
    val lines = mutableListOf<String>()
    private var cycle = 1
    private var currentLine = ""

    fun draw() {
        currentLine += sprite.pixelToRender(cycle)
        if (currentLine.length == size) {
            lines.add(currentLine)
            currentLine = ""
        }
        cycle = (cycle + 1) % size
    }
}

fun day10part1(input: List<String>): Int {
    var cycle = 0
    var registerX = 1
    val valuesAtCycle: MutableMap<Int, Int> = mutableMapOf()

    input.forEach {
        val ops = it.split(" ")
        cycle ++
        valuesAtCycle[cycle] = registerX
        if (ops[0] == "addx") {
            cycle ++
            valuesAtCycle[cycle] = registerX
            registerX += ops[1].toInt()
        }
    }

    return valuesAtCycle.getSignalStrengthAtCycles(listOf(20, 60, 100, 140, 180, 220))
}

fun day10part2(input: List<String>) {
    val sprite = Sprite()
    val crt = CRT(sprite = sprite)

    input.forEach {
        crt.draw()
        val ops = it.split(" ")
        if (ops[0] == "addx") {
            crt.draw()
            sprite.move(ops[1].toInt())
        }
    }

    crt.lines.forEach {
        println(it)
    }
}

fun main() {
    val input = readInput("Day10")

    println(day10part1(input))
    println(day10part2(input))
}
