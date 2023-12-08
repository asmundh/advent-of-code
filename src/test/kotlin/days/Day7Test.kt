package days

import aoc23.Hand
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day7Test {

    @Test
    fun shouldSortCorrectly() {
        val given = listOf(
            "77777",
            "77777",
            "88888"
        ).map { Hand(it, 0) }

        val actual = given.sortedDescending()
        val expected = listOf(
            "88888",
            "77777",
            "77777",
        ).map { Hand(it, 0) }

        assertEquals(
            expected,
            actual
        )
    }
}
