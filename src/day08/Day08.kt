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

fun String.splitToIntList(): List<Int> =
    this.map { it.toString().toInt() }

fun main() {
    fun part1(input: List<String>): Int {
        val treeLines = input.map { it.splitToIntList() }
        var count = 0
//        treeLines.subList(0, treeLines.lastIndex - 1).forEachIndexed {rowIndex, trees ->
//            trees.subList(0, trees.lastIndex - 1).forEachIndexed { treesIndex, _ ->
        treeLines.forEachIndexed { rowIndex, trees ->
            trees.forEachIndexed { treesIndex, _ ->
                val height = treeLines[rowIndex][treesIndex]
                val bools = listOf(
                        treeLines.heightAbove(rowIndex, treesIndex) < height ||
                            treeLines.heightBelow(rowIndex, treesIndex) < height ||
                            treeLines.heightLeft(rowIndex, treesIndex) < height ||
                            treeLines.heightRight(rowIndex, treesIndex) < height
                    )
                if (bools.any { it }) count += 1
                println("${rowIndex + 1} - ${treesIndex + 1} - $count\n" +
                        "Above: ${bools[0]} \n" +
                        "Below: ${bools.getOrNull(1)} \n" +
                        "Left: ${bools.getOrNull(2)} \n" +
                        "Right: ${bools.getOrNull(3)} \n")
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val sum = input.sumOf { it.length }
        return sum
    }

    val input = readInput("Day08")
//    println(part1(input))
    println(part1(listOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390",
    )))
//    println(part2(input))
}
