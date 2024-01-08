package ch.pikusa.drawing.commands

import ch.pikusa.drawing.ApplicationState
import ch.pikusa.drawing.Canvas

class CreateCanvas(val width: Int, val height: Int, val appState: ApplicationState) : DrawingCommand {

    override fun execute() {
        appState.canvas = Canvas(width, height)
    }
}