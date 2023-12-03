package datastructures

data class Coordinate(
    var x: Int,
    var y: Int
) {
    private fun touchesX(other: Coordinate): Boolean = (this.x == other.x) ||
        (this.x == (other.x - 1)) ||
        (this.x == (other.x + 1))

    private fun touchesY(other: Coordinate): Boolean =
        (this.y == other.y) ||
            (this.y == (other.y - 1)) ||
            (this.y == (other.y + 1))

    fun touches(other: Coordinate): Boolean = touchesX(other) && touchesY(other)
}

fun Coordinate.getAllAdjacentCoordinates(): List<Coordinate> =
    listOf(
        this.aboveLeft(),
        this.above(),
        this.aboveRight(),
        this.left(),
        this.right(),
        this.belowLeft(),
        this.below(),
        this.belowRight(),
    )

fun Coordinate.above(): Coordinate = this.copy(y = y - 1)
fun Coordinate.below(): Coordinate = this.copy(y = y + 1)

fun Coordinate.left(): Coordinate = this.copy(x = x - 1)

fun Coordinate.right(): Coordinate = this.copy(x = x + 1)

fun Coordinate.aboveRight(): Coordinate = this.above().right()

fun Coordinate.aboveLeft(): Coordinate = this.above().left()

fun Coordinate.belowRight(): Coordinate = this.below().right()

fun Coordinate.belowLeft(): Coordinate = this.below().left()
