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
    return 0L
}

fun SeedMapRoute.traverse() {
    seeds.forEach outer@{ seed ->
        plantMaps.forEach mapping@{ plantMappings ->
            plantMappings.forEach innerPlantMap@{ plantMap ->
                with (plantMap) {
                    if (seed.currentValue > source && seed.currentValue < source + plusBy) {
                        seed.currentValue = destination + seed.currentValue - source
                        seed.moved = true
                        return@mapping
                    }
                }
            }
        }
    }
}

fun getSeeds(inputSeeds: String) = inputSeeds.substringAfter(':').getLongList().map { Seed(startValue = it, currentValue = it) }

fun getPlantMap(section: List<String>) =
    section.drop(1).map { row ->  row.getLongList()
        .let {
            PlantMap(
                source = it[1],
                destination = it[0],
                plusBy = it[2]
            )
        }
    }

fun getSeedMapRoute(input: List<List<String>>): SeedMapRoute {
    return SeedMapRoute(
        seeds = getSeeds(input.first().first()),
        plantMaps = listOf(
            getPlantMap(input[1]),
            getPlantMap(input[2]),
            getPlantMap(input[3]),
            getPlantMap(input[4]),
            getPlantMap(input[5]),
            getPlantMap(input[6]),
            getPlantMap(input[7]),
        )
//        seedToSoilMap = getPlantMap(input[1]),
//        soilToFertilizerMap = getPlantMap(input[2]),
//        fertilizerToWaterMap = getPlantMap(input[3]),
//        waterToLightMap = getPlantMap(input[4]),
//        lightToTemperatureMap = getPlantMap(input[5]),
//        temperatureToHumidity = getPlantMap(input[6]),
//        humidityToLocationMap = getPlantMap(input[7]),
    )
}

data class SeedMapRoute(
    val seeds: List<Seed>,
    val plantMaps: List<List<PlantMap>>
//    val seedToSoilMap: List<PlantMap>,
//    val soilToFertilizerMap: List<PlantMap>,
//    val fertilizerToWaterMap: List<PlantMap>,
//    val waterToLightMap: List<PlantMap>,
//    val lightToTemperatureMap: List<PlantMap>,
//    val temperatureToHumidity: List<PlantMap>,
//    val humidityToLocationMap: List<PlantMap>,
)

data class Seed(
    val startValue: Long,
    var currentValue: Long,
    var moved: Boolean = false
)

data class PlantMap(
    val destination: Long,
    val source: Long,
    val plusBy: Long
)

fun main() {
    val input: List<List<String>> = InputReader.getInputListSeparatedByBlankLines(5)
    runTask("D4p1") { day5part1(input) }
//    runTask("D4p2") { day5part2(listOf()) }
}
