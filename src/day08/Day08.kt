package day08

import readInput

fun List<List<Int>>.heightAbove(y: Int, x: Int): Int =
    if (y == 0) - 1
    else this[y - 1][x]

fun List<List<Int>>.heightBelow(y: Int, x: Int): Int =
    if (y == this.lastIndex) -1
    else this[y + 1][x]

fun List<List<Int>>.heightRight(y: Int, x: Int): Int =
    if (this[y].lastIndex == x) -1
    else this[y][x + 1]

fun List<List<Int>>.heightLeft(y: Int, x: Int): Int =
    if (x == 0) - 1
    else this[y][x - 1]

fun List<List<Int>>.isVisibleAbove(y: Int, x: Int): Boolean {
    for (idxAbove in y downTo 1 step 1) {
        val treeToCheck = this.heightAbove(idxAbove, x)
        val currentTree = this[y][x]
        if (treeToCheck == -1) return true
        if (treeToCheck >= currentTree) {
            return false
        }
    }
    return true
}

fun List<List<Int>>.isVisibleBelow(y: Int, x: Int): Boolean {
    for (idxBelow in y .. this.size) {
        val treeToCheck = this.heightBelow(idxBelow, x)
        val currentTree = this[y][x]
        if (treeToCheck == -1) return true
        if (treeToCheck >= this[y][x]) {
            return false
        }
    }
    return true
}

fun List<List<Int>>.isVisibleRight(y: Int, x: Int): Boolean {
    for (idxRight in x .. this[y].size) {
        val treeToCheck = this.heightRight(y, idxRight)
        val currentTree = this[y][x]
        if (treeToCheck == -1) return true
        if (treeToCheck >= this[y][x]) {
            return false
        }
    }
    return true
}

fun List<List<Int>>.isVisibleLeft(y: Int, x: Int): Boolean {
    for (idxLeft in x downTo 0 step 1) {
        val treeToCheck = this.heightLeft(y, idxLeft)
        val currentTree = this[y][x]
        if (treeToCheck == -1) return true
        if (treeToCheck >= this[y][x]) {
            return false
        }
    }
    return true
}

fun String.splitToIntList(): List<Int> =
    this.map { it.toString().toInt() }

fun part1(input: List<String>): Int {
    val treeLines = input.map { it.splitToIntList() }
    var count = 0
    treeLines.forEachIndexed { y, trees ->
        trees.forEachIndexed { x, _ ->
            val bools = listOf(
                treeLines.isVisibleAbove(y, x) ||
                        treeLines.isVisibleBelow(y, x)||
                        treeLines.isVisibleLeft(y, x) ||
                        treeLines.isVisibleRight(y, x)
            )
            if (bools.any { it }) count += 1
//            println("${y + 1} - ${x + 1} - $count\n" +
//                    "Above: ${bools[0]} \n" +
//                    "Below: ${bools.getOrNull(1)} \n" +
//                    "Left: ${bools.getOrNull(2)} \n" +
//                    "Right: ${bools.getOrNull(3)} \n")
        }
    }
    return count
}

fun part2(input: List<String>): Int {
    val sum = input.sumOf { it.length }
    return sum
}

fun main() {
    val aocTestInput = listOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390",
    )
    val testInputFake = listOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "95380",
        "35370",
        "35390",
    )


    val input = readInput("Day08")
//    println(part1(aocTestInput))
    println(part1(input))
//    println(testInput.map { it.splitToIntList() }.isVisibleAbove(3, 3))
//    println(testInput.map { it.splitToIntList() }.isVisibleBelow(4, 3))
//    println(testInput.map { it.splitToIntList() }.isVisibleRight(5, 1))
//    println(testInput.map { it.splitToIntList() }.isVisibleLeft(4, 3))
//    println(part2(input))
}