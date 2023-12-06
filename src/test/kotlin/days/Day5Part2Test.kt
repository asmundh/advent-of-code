package days

import aoc23.PlantMap
import aoc23.Seed2
import aoc23.SeedMapRoute
import aoc23.traversePart2
import org.junit.jupiter.api.Test
import kotlin.math.exp
import kotlin.test.assertEquals

class Day5Part2Test {
    @Test
    fun verifyBehavior() {
        val plantMaps: List<PlantMap> = listOf(
            PlantMap(50, 20, 10)
        )
        val seedMapRoute = SeedMapRoute(
            seeds = emptyList(),
            seeds2 = listOf(
                Seed2(10, 25)
            ),
            categories = listOf(plantMaps)
        )

        val actual = seedMapRoute.traversePart2()

        val expected = listOf(
            Seed2(10, 19),
            Seed2(50, 55)
        )

        assertEquals(expected, actual)
    }

    @Test
    fun verify2() {
        val plantMaps: List<PlantMap> = listOf(
            PlantMap(50, 20, 10)
        )
        val seedMapRoute = SeedMapRoute(
            seeds = emptyList(),
            seeds2 = listOf(
                Seed2(15, 35)
            ),
            categories = listOf(plantMaps)
        )

        val actual = seedMapRoute.traversePart2()

        val expected = listOf(
            Seed2(15, 19), // lower than source range, unchanged
            Seed2(30, 35), // higher than source range, unchanged
            Seed2(50, 59), // transformed
        )

        assertEquals(expected, actual)
    }
}
