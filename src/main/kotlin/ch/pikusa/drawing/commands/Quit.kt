package ch.pikusa.drawing.commands

import ch.pikusa.drawing.ApplicationState

class Quit(val applicationState: ApplicationState) : DrawingCommand {

    override fun execute() {
        applicationState.isRunning = false
    }
}