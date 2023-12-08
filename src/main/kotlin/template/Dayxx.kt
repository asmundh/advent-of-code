package template

import runTask
import utils.InputReader

fun dayxpart1(input: List<String>): Int {
    return 0
}

fun dayxpart2(input: List<String>): Int {
    return 1
}

fun main() {
    val input = InputReader.getInputAsList<String>(1)
    runTask("DxP1") { dayxpart1(input) }
    runTask("DxP2") { dayxpart2(input) }
}
