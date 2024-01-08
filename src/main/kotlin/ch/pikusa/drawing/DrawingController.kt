package ch.pikusa.drawing

/**
 * Controller for drawing application, maintains application state and executes user commands.
 */
class DrawingController(private val applicationState: ApplicationState = ApplicationState()) {
    private val commandParser = CommandParser(applicationState)

    val canvas: Canvas?
        get() = applicationState.canvas

    val isRunning: Boolean
        get() = applicationState.isRunning

    fun handleCommand(userCommand: String) {
        val command = commandParser.parse(userCommand)
        command.execute()
    }
}


