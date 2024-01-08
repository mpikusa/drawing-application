package ch.pikusa.drawing

import ch.pikusa.drawing.commands.*
import java.util.*

/**
 * Class responsible for parsing user input, validating it and creating DrawingCommand objects.
 */
class CommandParser(private val applicationState: ApplicationState) {

    fun parse(rawCommand: String): DrawingCommand {
        val splitCommand = rawCommand.trimStart().split("\\s+".toRegex())
        return when (splitCommand.first().uppercase(Locale.getDefault())) {
            "Q" -> createQuitCommand()
            "C" -> createCreateCanvasCommand(splitCommand)
            "L" -> createDrawLineCommand(splitCommand)
            "R" -> createDrawRectangleCommand(splitCommand)
            "B" -> createFillCommand(splitCommand)
            else -> throw DrawingException("Command not supported!")
        }
    }

    private fun createQuitCommand(): DrawingCommand {
        return Quit(applicationState)
    }

    private fun createCreateCanvasCommand(command: List<String>): DrawingCommand {
        command.checkCommandSize(3, "Create canvas command requires 2 numerical parameters (C width height)")
        val (_, widthString, heightString) = command
        val width = parseNumber(widthString)
        val height = parseNumber(heightString)
        return CreateCanvas(width, height, applicationState)
    }

    private fun createDrawRectangleCommand(command: List<String>): DrawingCommand {
        command.checkCommandSize(5, "Draw rectangle command requires 4 numerical parameters (R x1 y1 x2 y2)")
        val (_, x1, y1, x2, y2) = command
        val topLeft = createPoint(x1, y1)
        val bottomRight = createPoint(x2, y2)
        return DrawRectangle(topLeft, bottomRight, applicationState)
    }

    private fun createDrawLineCommand(command: List<String>): DrawingCommand {
        command.checkCommandSize(5, "Draw line command requires 4 numerical parameters (L x1 y1 x2 y2)")
        val (_, x1, y1, x2, y2) = command
        val p1 = createPoint(x1, y1)
        val p2 = createPoint(x2, y2)
        return DrawLine(p1, p2, applicationState)
    }

    private fun createFillCommand(command: List<String>): DrawingCommand {
        command.checkCommandSize(4, "Fill command requires 3 parameters (B x y c)!")
        val (_, xString, yString, colorString) = command
        val point = createPoint(xString, yString)
        val color = when(colorString.length) {
            0 -> ' '
            1 -> colorString[0]
            else -> throw DrawingException("Color value must be a single character: $colorString")
        }
        return Fill(point, color, applicationState)
    }

    private fun createPoint(xs: String, ys: String): Point {
        return Point(x = parseNumber(xs), y = parseNumber(ys))
    }

    private fun parseNumber(s: String): Int {
        return s.toIntOrNull() ?: throw DrawingException("Incorrect value, expected number: $s")
    }

    private fun List<String>.checkCommandSize(minElements: Int, message: String) {
        if(this.size < minElements) {
            throw DrawingException(message)
        }
    }
}