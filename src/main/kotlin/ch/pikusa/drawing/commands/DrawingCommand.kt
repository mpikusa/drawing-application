package ch.pikusa.drawing.commands

import ch.pikusa.drawing.Canvas
import ch.pikusa.drawing.DrawingException
import ch.pikusa.drawing.Point

/**
 * Interface for commands that can be executed within Drawing application
 */
interface DrawingCommand {

    fun execute()
}