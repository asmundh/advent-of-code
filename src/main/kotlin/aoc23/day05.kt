package aoc23

import getLongList
import runTask
import utils.InputReader

fun day5part1(input: List<List<String>>): Long {
    val seedMapRoute = getSeedMapRoute(input)
    seedMapRoute.traverse()
    return seedMapRoute.seeds.minOf { it.currentValue }
}

fun day5part2(input: List<List<String>>): Long {
    val seedMapRoute = getSeedMapRoute(input)
    return seedMapRoute.traversePart2().minOf { it.lower }
}

fun SeedMapRoute.traverse() {
    seeds.asSequence().forEach { seed ->
        categories.asSequence().forEach mapping@{ plantMappings ->
            plantMappings.asSequence().forEach { plantMap ->
                with(plantMap) {
                    if (seed.currentValue >= source && seed.currentValue <= source + plusBy) {
                        seed.currentValue = destination + seed.currentValue - source
                        return@mapping
                    }
                }
            }
        }
    }
}

fun SeedMapRoute.traversePart2(): List<Seed2> {
    return seeds2.flatMap { seedRange ->

        var seedRanges: List<Seed2> = listOf(seedRange)

        categories.asSequence().forEach { category ->
            for (i in seedRanges.indices) {
                category.asSequence().forEach { plantMap ->
                    if (!seedRanges.all { it.mappedByCategory }) {
                        seedRanges = seedRanges.flatMap { it.applyMap(plantMap) }
                    }
                }
            }
            seedRanges.forEach { it.mappedByCategory = false }
        }
        seedRanges
    }
}
fun getSeedMapRoute(input: List<List<String>>) =
    SeedMapRoute(
        seeds = getSeeds(input.first().first()),
        seeds2 = getSeedsPart2(input.first().first()),
        categories = input.drop(1).map { getPlantMap(it) }
    )

fun getSeeds(inputSeeds: String): List<Seed> =
    inputSeeds.substringAfter(':').getLongList().map { Seed(startValue = it, currentValue = it) }

fun getSeedsPart2(inputSeeds: String): List<Seed2> =
    inputSeeds.substringAfter(':').getLongList()
        .chunked(2)
        .map {
            Seed2(
                it.first(),
                it.first() + it.last()
            )
        }

fun getPlantMap(section: List<String>): List<PlantMap> =
    section.drop(1).map { row ->
        row.getLongList()
            .let {
                PlantMap(
                    source = it[1],
                    destination = it[0],
                    plusBy = it[2]
                )
            }
    }

data class SeedMapRoute(
    val seeds: List<Seed>,
    val seeds2: List<Seed2>,
    val categories: List<List<PlantMap>>
)

data class Seed(
    val startValue: Long,
    var currentValue: Long
)

data class Seed2(
    val lower: Long,
    val upper: Long,
    var mappedByCategory: Boolean = false
) {
    fun applyMap(plantMap: PlantMap): List<Seed2> {
        if (mappedByCategory || upper < plantMap.source || lower > plantMap.upperLimitSource()) {
            return listOf(this)
        }

        if (upper > plantMap.upperLimitSource() && lower < plantMap.source) { // Spans entire range +more
            return listOf(
                Seed2(lower, plantMap.source - 1),
                Seed2(plantMap.upperLimitSource() + 1, upper),
                Seed2(plantMap.source + plantMap.getStep(), plantMap.upperLimitSource() + plantMap.getStep(), true),
            )
        }

        val newSeeds: MutableList<Seed2> = mutableListOf()
        var newLower = lower
        var newUpper = upper

        if (lower < plantMap.source && (upper in plantMap.source..plantMap.upperLimitSource())) {
            newSeeds.add(
                Seed2(lower, plantMap.source - 1)
            )
            newLower = plantMap.source
        }

        if (upper > plantMap.upperLimitSource() && (lower in plantMap.source..plantMap.upperLimitSource())) {
            newSeeds.add(
                Seed2(plantMap.upperLimitSource() + 1, upper)
            )
            newUpper = plantMap.upperLimitSource()
        }
        newSeeds.add(
            Seed2(
                lower = newLower + plantMap.getStep(),
                upper = newUpper + plantMap.getStep(),
                mappedByCategory = true
            )
        )
        return newSeeds
    }
}

data class PlantMap(
    val destination: Long,
    val source: Long,
    val plusBy: Long
) {
    fun upperLimitSource() = source + plusBy - 1
    fun getStep(): Long =
        destination - source
}

fun main() {
    val input: List<List<String>> = InputReader.getInputListSeparatedByBlankLines(5)
    runTask("D5p1") { day5part1(input) }
    runTask("D5p2") { day5part2(input) }
}
