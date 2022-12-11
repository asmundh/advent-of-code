package day11

import lcm
import readInput

data class Monkey(
    val name: String,
    val items: MutableList<Long> = mutableListOf(),
    val operation: String,
    val divisor: Long,
    val ifTrueTarget: Int,
    val ifFalseTarget: Int,
    var inspections: Long = 0
) {
    fun doRound(worryDivisor: Long, leastCommonMultiplier: Long): Map<Int, List<Long>> {
        val itemsToThrow = mutableMapOf<Int, MutableList<Long>>(
            ifTrueTarget to mutableListOf(),
            ifFalseTarget to mutableListOf(),
        )

        items.forEach {
            val newWorryLevel = doOperation(it) % leastCommonMultiplier
            val boredLevel = Math.floorDiv(newWorryLevel, worryDivisor)

            itemsToThrow[getMonkeyTarget(boredLevel)]?.add(boredLevel)
            inspections ++
        }
        items.clear()
        return itemsToThrow
    }

    private fun doOperation(old: Long): Long {
        val op = operation.split(" ")
        val second = op[2].let {
            if (it == "old") old
            else it.toLong()
        }
        return when (op[1]) {
            "*" -> old * second
            "+" -> old + second
            else -> throw Exception("Ulovlig mattestykke: $operation ga meg $op")
        }
    }

    private fun getMonkeyTarget(testOutput: Long) = if ((testOutput % divisor) == 0L) ifTrueTarget else ifFalseTarget
}

data class MonkeyGang(
    val monkeys: List<Monkey>,
    val rounds: Int = 20,
    val worryDivisor: Long = 1,
) {
    private var roundCount = 0
    private val leastCommonMultiplier = monkeys.map { it.divisor }.reduce { acc, i -> lcm(acc, i) }

    fun trigger() {
        while (roundCount < rounds) {
            doRound()
            roundCount++
        }
    }

    private fun doRound() {
        monkeys.forEach { monkey ->
            val toDistribute = monkey.doRound(worryDivisor, leastCommonMultiplier)
            toDistribute.forEach {
                monkeys[it.key].items.addAll(it.value)
            }
        }
    }

    fun measureMonkeyBusinessLevel() = monkeys
        .map { it.inspections }
        .sortedDescending()
        .let { it[0] * it[1] }
}

fun List<String>.parseToMonkey(): Monkey =
    Monkey(
        name = this[0].split(" ")[1],
        items = this[1].split(":")[1].split(",").mapTo(mutableListOf()) { it.trim().toLong() },
        operation = this[2].split("= ")[1],
        divisor = this[3].split("by ")[1].toLong(),
        ifTrueTarget = this[4].split("monkey ")[1].toInt(),
        ifFalseTarget = this[5].split("monkey ")[1].toInt(),
    )

fun part1(input: List<String>): Long {
    val monkeys = input.chunked(7).map { it.parseToMonkey() }
    val monkeyGang = MonkeyGang(monkeys = monkeys, worryDivisor = 3)
    monkeyGang.trigger()
    println(monkeyGang.monkeys.map { it.inspections })
    return monkeyGang.measureMonkeyBusinessLevel()
}

fun part2(input: List<String>): Long {
    val monkeys = input.chunked(7).map { it.parseToMonkey() }
    val monkeyGang = MonkeyGang(monkeys = monkeys, rounds = 10000)
    monkeyGang.trigger()
    println(monkeyGang.monkeys.map { it.inspections })
    return monkeyGang.measureMonkeyBusinessLevel()
}

fun main() {
    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
