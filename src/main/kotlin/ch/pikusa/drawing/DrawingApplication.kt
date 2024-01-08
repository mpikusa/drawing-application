package ch.pikusa.drawing

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val controller = DrawingController()

    while(controller.isRunning) {
        print("enter command: ")
        val input = scanner.nextLine()

        try {
            controller.handleCommand(input)
        } catch (de: DrawingException) {
            println("Error: ${de.message}")
        }

        if(controller.isRunning) {
            controller.canvas
                ?.render(withFrame = true)
                ?.let { it.forEach { line -> println(line) } }
        }
    }
}



