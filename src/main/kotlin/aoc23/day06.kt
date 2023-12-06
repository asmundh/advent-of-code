package aoc23

import getLongList
import runTask
import utils.InputReader

fun day6part1(input: List<String>): Int {
    val (durations, distancesToBeat) =
        input.map { it.substringAfter(':').getLongList() }

    return durations.mapIndexed { index, duration ->
        var timesBeat = 0
        for (timeSpentAccelerating in 0..duration) {
            val distance = timeSpentAccelerating * (duration - timeSpentAccelerating)
            if (distance > distancesToBeat[index]) {
                timesBeat++
            }
        }
        timesBeat
    }.reduce { acc, i -> acc * i}
}

fun day6part2(input: List<String>): Int {
    val duration = input.first().filter { it.isDigit() }.toLong()
    val distanceToBeat = input.last().filter { it.isDigit() }.toLong()

    var timesBeat = 0
    for (timeSpentAccelerating in 0..duration) {
        val distance = timeSpentAccelerating * (duration - timeSpentAccelerating)
        if (distance > distanceToBeat) {
            timesBeat++
        }
    }
    return timesBeat
}

fun main() {
    val input = InputReader.getInputAsList<String>(6)
    runTask("DxP1") {day6part1(input) }
    runTask("DxP2") {day6part2(input) }
}
