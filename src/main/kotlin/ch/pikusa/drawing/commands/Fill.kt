package ch.pikusa.drawing.commands

import ch.pikusa.drawing.ApplicationState
import ch.pikusa.drawing.Point
import java.util.*

/**
 *  Fills the entire area connected to startingPoint with "color" c. The behavior of this operation is the same as
 *  that of the "bucket fill" tool in paint programs.
 */
class Fill(val startingPoint: Point, val color: Char, val applicationState: ApplicationState) : DrawingCommand {

    init {
        applicationState.canvas.verifyCanvasIsCreated()
        applicationState.canvas?.verifyPointBelongsToCanvas(startingPoint)
    }

    override fun execute() {
        val canvas = applicationState.canvas!!
        val startingPointColor = canvas[startingPoint]

        if(startingPointColor == color) {
            return
        }

        val directions = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))
        val queue: Queue<Point> = LinkedList()
        queue.add(startingPoint)

        while (queue.isNotEmpty()) {
            val currentPoint = queue.poll()
            canvas[currentPoint] = color
            val validNeighbouringPoints = directions.map { currentPoint.shift(it) }
                .filterNot { canvas.isOutOfBounds(it) }
                .filter { canvas[it] == startingPointColor }

            queue.addAll(validNeighbouringPoints)
        }
    }

    private fun Point.shift(s: Pair<Int, Int>): Point = Point(this.x + s.first, this.y + s.second)

}