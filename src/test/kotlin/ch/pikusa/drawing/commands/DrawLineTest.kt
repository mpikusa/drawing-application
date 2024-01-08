package ch.pikusa.drawing.commands

import ch.pikusa.drawing.DrawingException
import ch.pikusa.drawing.Point
import ch.pikusa.drawing.createApplicationState
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class DrawLineTest  : StringSpec() {
    init {
        "Should throw exception if the line is not vertical or horizontal" {
            val applicationState = createApplicationState(10)
            shouldThrow<DrawingException> {
                DrawLine(Point(3, 3), Point(6, 6), applicationState).execute()
            }
        }

        forAll(
            row(-1, 2, -1, 2),
            row(1, 1, 5, 1),
            row(3, 3, 3, 8),
            row(4, 4, 8, 4),
        ) { x1, y1, x2, y2 ->
            "Should throw exception if points given to draw the line is beyond canvas (($x1, $y1), ($x2, $y2))" {
                shouldThrow<DrawingException> {
                    DrawLine(Point(x1, y1), Point(x2, y2), createApplicationState(3)).execute()
                }
            }
        }

        "Should correctly draw horizontal line" {
            val applicationState = createApplicationState(3)

            DrawLine(Point(1, 2), Point(3, 2), applicationState).execute()

            val canvasPrintout = applicationState.canvas!!.render()
            canvasPrintout shouldBe listOf(
                "   ",
                "xxx",
                "   "
            )
        }

        "Should draw line on the edge of the canvas" {
            val applicationState = createApplicationState(3)

            DrawLine(Point(1, 1), Point(3, 1), applicationState).execute()

            applicationState.canvas!!.render() shouldBe listOf(
                "xxx",
                "   ",
                "   "
            )
        }

        "Should correctly draw a vertical line" {
            val applicationState = createApplicationState(5)

            DrawLine(Point(2, 2), Point(2, 5), applicationState).execute()

            applicationState.canvas!!.render() shouldBe listOf(
                "     ",
                " x   ",
                " x   ",
                " x   ",
                " x   "
            )
        }
    }
}