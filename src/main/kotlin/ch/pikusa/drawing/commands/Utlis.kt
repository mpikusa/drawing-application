package ch.pikusa.drawing.commands

import ch.pikusa.drawing.Canvas
import ch.pikusa.drawing.DrawingException
import ch.pikusa.drawing.Point

fun Canvas?.verifyCanvasExists() {
    if(this == null) {
        throw DrawingException("Operation requires canvas to exist, please execute Create Canvas command first.")
    }
}

fun Canvas.verifyPointBelongsToCanvas(p: Point) {
    if (this.isOutOfBounds(p)) {
        throw DrawingException("$p is not a part of the canvas.")
    }
}

fun Canvas.drawLine(p1: Point, p2: Point) {
    for (y in (p1.y..p2.y)) {
        for (x in (p1.x..p2.x)) {
            this[Point(x, y)] = Canvas.DEFAULT_COLOR
        }
    }
}