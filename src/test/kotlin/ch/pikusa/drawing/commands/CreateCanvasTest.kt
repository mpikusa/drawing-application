package ch.pikusa.drawing.commands

import ch.pikusa.drawing.ApplicationState
import ch.pikusa.drawing.Canvas
import ch.pikusa.drawing.DrawingException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class CreateCanvasTest : StringSpec() {
    init {
        "Should create a new canvas when previous canvas is empty" {
            val applicationState = ApplicationState()

            CreateCanvas(4, 7, applicationState).execute()

            applicationState.canvas.shouldNotBeNull()
            applicationState.canvas!!.width shouldBe 4
            applicationState.canvas!!.height shouldBe 7
        }

        "Should create a new canvas when previous canvas is not empty" {
            val applicationState = ApplicationState(canvas = Canvas(9, 9))

            CreateCanvas(4, 7, applicationState).execute()

            applicationState.canvas.shouldNotBeNull()
            applicationState.canvas!!.width shouldBe 4
            applicationState.canvas!!.height shouldBe 7
        }

        forAll(
            row(-1, 2),
            row(0, 1),
            row(5, -1),
            row(5, 0),
            row(0, 0),
            row(-5, -1),

            ) { width, height ->
            "Should throw DrawingException if both canvas dimensions are not greater than 0 (w: $width, h: $height)" {
                val applicationState = ApplicationState()

                shouldThrow<DrawingException> { CreateCanvas(width, height, applicationState).execute() }
            }
        }
    }
}