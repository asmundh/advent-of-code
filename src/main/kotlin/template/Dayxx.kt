package template

import runTask
import utils.InputReader

fun part1(input: List<String>): Int {
    return 0
}

fun part2(input: List<String>): Int {
    return 1
}

fun main() {
    val input = InputReader.getInputAsList<String>(1)
    runTask("DxP1") { part1(input) }
    runTask("DxP2") { part2(input) }
}
