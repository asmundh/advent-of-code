package aoc23

import datastructures.Coordinate
import datastructures.getAllAdjacentCoordinates
import utils.InputReader
import java.util.UUID

fun day3part1(input: List<String>): Int =
    with(getNumbersAndSymbols(input)) {
        symbols.flatMap { symbol ->
            symbol.coordinate.getAllAdjacentCoordinates()
                .mapNotNull { coordinate ->
                    parts[coordinate]
                }
        }.distinctBy { it.id }.sumOf { it.size }
    }

fun day3part2(input: List<String>): Int =
    with(getNumbersAndSymbols(input)) {
        symbols
            .filter { it.char == '*' }
            .sumOf { symbol ->
                symbol.coordinate.getAllAdjacentCoordinates().mapNotNull { coordinate ->
                    parts[coordinate]
                }.distinctBy { it.id }
                    .takeIf { it.size == 2 }
                    ?.let { it.first().size * it.last().size } ?: 0
            }
    }

fun getNumbersAndSymbols(input: List<String>): SymbolsAndParts {
    val parts: MutableMap<Coordinate, Part> = mutableMapOf()
    val symbols: MutableList<Symbol> = mutableListOf()
    input.forEachIndexed { indexY, row ->
        var candidate = ""
        row.forEachIndexed { indexX, char ->
            if (char.isDigit()) {
                candidate += char
            } else if (char != '.') {
                symbols.add(Symbol(Coordinate(indexX + 1, indexY), char))
            }
            if (indexX == row.lastIndex || !char.isDigit()) {
                if (candidate.isNotEmpty()) {
                    val uuid = UUID.randomUUID().toString()
                    candidate.indices.forEach {
                        val coordinate = Coordinate(indexX - it, indexY)
                        parts[coordinate] = Part(coordinate, uuid, candidate.toInt())
                    }
                }
                candidate = ""
            }
        }
    }
    return SymbolsAndParts(symbols, parts)
}

data class Part(
    val coordinate: Coordinate,
    val id: String,
    val size: Int
)

data class Symbol(
    val coordinate: Coordinate,
    val char: Char
)

data class SymbolsAndParts(
    val symbols: List<Symbol>,
    val parts: Map<Coordinate, Part>
)

fun main() {
    val input: List<String> = InputReader.getInputAsList(3)
    println(day3part1(input))
    println(day3part2(input))
}
