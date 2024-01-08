package ch.pikusa.drawing.commands

import ch.pikusa.drawing.ApplicationState
import ch.pikusa.drawing.DrawingException
import ch.pikusa.drawing.Point

/**
 * Draws a line on the application's canvas between two points.
 * Only vertical or horizontal lines are allowed. Order of points doesn't matter
 */
class DrawLine(val p1: Point, val p2: Point, val applicationState: ApplicationState) : DrawingCommand {

    init {
        verifyThatLineIsNotTilted(p1, p2)
        applicationState.canvas.verifyCanvasIsCreated()
        applicationState.canvas?.verifyPointBelongsToCanvas(p1)
        applicationState.canvas?.verifyPointBelongsToCanvas(p2)
    }

    override fun execute() {
        val sortedPoints = if (p1.x < p2.x || p1.y < p2.y) Pair(p1, p2) else Pair(p2, p1)

        applicationState.canvas?.drawLine(sortedPoints.first, sortedPoints.second)
    }

    private fun verifyThatLineIsNotTilted(p1: Point, p2: Point) {
        if (!(p1.y == p2.y || p1.x == p2.x)) {
            throw DrawingException("Only vertical and horizontal lines are allowed.")
        }
    }
}