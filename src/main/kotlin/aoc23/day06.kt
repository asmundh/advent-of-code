package aoc23
import getIntegersList
import runTask
import utils.InputReader

fun day6part1(input: List<String>): Int {
    val durations = input.first().substringAfter(':').getIntegersList()
    val distancesToBeat = input.last().substringAfter(':').getIntegersList()

    val beatings: MutableMap<Int, Int> = mutableMapOf()

    durations.forEachIndexed { index, duration ->
        var timesBeat = 0
        for (timeSpentAccelerating in 0..duration) {
            val startSpeed = timeSpentAccelerating
            val distance = startSpeed * (duration - timeSpentAccelerating)
            if (distance > distancesToBeat[index]) {
                timesBeat ++
            }
        }
        beatings[index] = timesBeat
    }

    return beatings.values.reduce { acc, i -> acc * i}
}

fun day6part2(input: List<String>): Int {
    val duration = input.first().filter { it.isDigit() }.toLongOrNull()!!
    val distanceToBeat = input.last().filter { it.isDigit() }.toLongOrNull()!!

    var timesBeat = 0
    for (timeSpentAccelerating in 0..duration) {
        val distance = timeSpentAccelerating * (duration - timeSpentAccelerating)
        if (distance > distanceToBeat) {
            timesBeat++
        }
    }

    return timesBeat
//    return beatings.values.reduce { acc, i -> acc * i}
}

fun main() {
    val input = InputReader.getInputAsList<String>(6)
    runTask("DxP1") {day6part1(input) }
    runTask("DxP2") {day6part2(input) }
}
