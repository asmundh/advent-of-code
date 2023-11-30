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
