package ch.pikusa.drawing

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe


class DrawingControllerTest: StringSpec() {

    init {
        "Integration test" {
            val drawingController = DrawingController()
            drawingController.canvas.shouldBeNull()

            drawingController.handleCommand("C 20 4")
            drawingController.canvas.shouldNotBeNull()
            drawingController.isRunning shouldBe true
            val afterCreate = drawingController.canvas!!.render(withFrame = true)

            afterCreate shouldBe listOf(
                "----------------------",
                "|                    |",
                "|                    |",
                "|                    |",
                "|                    |",
                "----------------------"
            )

            drawingController.handleCommand("L 1 2 6 2")
            drawingController.isRunning shouldBe true
            val afterDrawLine = drawingController.canvas!!.render(withFrame = true)

            afterDrawLine shouldBe listOf(
                "----------------------",
                "|                    |",
                "|xxxxxx              |",
                "|                    |",
                "|                    |",
                "----------------------"
            )

            drawingController.handleCommand("L 6 3 6 4")
            drawingController.isRunning shouldBe true
            val afterDrawLine2 = drawingController.canvas!!.render(withFrame = true)
            afterDrawLine2 shouldBe listOf(
                "----------------------",
                "|                    |",
                "|xxxxxx              |",
                "|     x              |",
                "|     x              |",
                "----------------------"
            )

            drawingController.handleCommand("R 14 1 18 3")

            val afterRectangle = drawingController.canvas!!.render(withFrame = true)
            drawingController.isRunning shouldBe true
            afterRectangle shouldBe listOf(
                "----------------------",
                "|             xxxxx  |",
                "|xxxxxx       x   x  |",
                "|     x       xxxxx  |",
                "|     x              |",
                "----------------------"
            )

            drawingController.handleCommand("B 10 3 o")
            val afterFill = drawingController.canvas!!.render(withFrame = true)
            drawingController.isRunning shouldBe true
            afterFill shouldBe listOf(
                "----------------------",
                "|oooooooooooooxxxxxoo|",
                "|xxxxxxooooooox   xoo|",
                "|     xoooooooxxxxxoo|",
                "|     xoooooooooooooo|",
                "----------------------"
            )

            drawingController.handleCommand("Q")
            drawingController.isRunning shouldBe false

        }
    }
}