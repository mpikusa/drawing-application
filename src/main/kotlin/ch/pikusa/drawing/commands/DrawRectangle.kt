package ch.pikusa.drawing.commands

import ch.pikusa.drawing.ApplicationState
import ch.pikusa.drawing.DrawingException
import ch.pikusa.drawing.Point


class DrawRectangle(val topLeft: Point, val bottomRight: Point, val applicationState: ApplicationState) :
    DrawingCommand {

    init {
        applicationState.canvas.verifyCanvasIsCreated()
        applicationState.canvas?.verifyPointBelongsToCanvas(topLeft)
        applicationState.canvas?.verifyPointBelongsToCanvas(bottomRight)
        verifyPointsAreInOrder(topLeft, bottomRight)
    }

    private fun verifyPointsAreInOrder(p1: Point, p2: Point) {
        if (!(p1.x < p2.x && p1.y < p2.y)) {
            throw DrawingException(
                "First point should be top left corner of the rectangle while second bottom right corner!"
            )
        }
    }

    override fun execute() {
        val topRight = bottomRight.copy(y = topLeft.y)
        val bottomLeft = topLeft.copy(y = bottomRight.y)
        val canvas = applicationState.canvas!!
        canvas.drawLine(topLeft, topRight)
        canvas.drawLine(topLeft, bottomLeft)
        canvas.drawLine(bottomLeft, bottomRight)
        canvas.drawLine(topRight, bottomRight)
    }
}