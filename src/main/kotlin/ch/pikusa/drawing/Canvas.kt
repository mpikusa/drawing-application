package ch.pikusa.drawing

/**
 * Canvas class representing a drawing area that allows only low level operations(get, set and render)
 */
class Canvas(val width: Int, val height: Int) {

    init {
        if (width <= 0)
            throw DrawingException("Canvas should have width that is greater than 0")

        if (height <= 0)
            throw DrawingException("Canvas should have height that is greater than 0")
    }

    private val drawingArray: Array<Array<Char>> = Array(height) { Array(width) { ' ' } }

    operator fun get(p: Point): Char {
        return drawingArray[p.y - 1][p.x - 1]
    }

    operator fun set(p: Point, color: Char) {
        drawingArray[p.y - 1][p.x - 1] = color
    }

    fun render(withFrame: Boolean = false): List<String> {
        val renderedCanvas = drawingArray.map { it.toList().joinToString("") }

        return if (withFrame) {
            val verticalEdge = listOf("-".repeat(renderedCanvas[0].length + 2))
            verticalEdge + renderedCanvas.map { "|$it|" } + verticalEdge
        } else
            renderedCanvas
    }

    fun isOutOfBounds(p: Point) = !(drawingArray.indices.contains(p.y - 1) && drawingArray[0].indices.contains(p.x - 1))

    companion object {
        const val DEFAULT_COLOR: Char = 'x'
    }
}

