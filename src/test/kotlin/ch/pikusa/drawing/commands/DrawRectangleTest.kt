package ch.pikusa.drawing.commands

import ch.pikusa.drawing.DrawingException
import ch.pikusa.drawing.Point
import ch.pikusa.drawing.createApplicationState
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class DrawRectangleTest: StringSpec() {
    init {

        forAll(
            row(-1, 2, -1, 2),
            row(1, 1, 4, 4),
            row(3, 3, 6, 9),
            row(4, 4, 8, 4),
        ) { x1, y1, x2, y2 ->
            "Should throw exception if point given to draw the rectangle is beyond the canvas ($x1, $y1, $x2, $y2)" {
                shouldThrow<DrawingException> {
                    DrawRectangle(Point(x1, y1), Point(x2, y2), createApplicationState(3)).execute()
                }
            }
        }

        "Should correctly draw rectangle" {
            val applicationState = createApplicationState(4)

            DrawRectangle(Point(1, 1), Point(3, 3), applicationState).execute()

            applicationState.canvas!!.render() shouldBe listOf(
                "xxx ",
                "x x ",
                "xxx ",
                "    "
            )
        }

        "Should correctly draw rectangle the same size as the canvas" {
            val applicationState = createApplicationState(4)

            DrawRectangle(Point(1, 1), Point(4, 4), applicationState).execute()

            applicationState.canvas!!.render() shouldBe listOf(
                "xxxx",
                "x  x",
                "x  x",
                "xxxx"
            )
        }

        "Should throw exception if first point is not an upper left corner of the rectangle" {
            val applicationState = createApplicationState(5)

            shouldThrow<DrawingException> {
                DrawRectangle(Point(4, 4), Point(1, 1), applicationState).execute()
            }
        }

        "Should throw exception if rectangle has width of 1" {
            shouldThrow<DrawingException> {
                DrawRectangle(Point(2, 2), Point(2, 5), createApplicationState(5)).execute()
            }
        }

        "should throw exception if rectangle has height of 1" {
            shouldThrow<DrawingException> {
                DrawRectangle(Point(2, 2), Point(5, 2), createApplicationState(5)).execute()
            }
        }

        "should throw exception if both points are the same" {
            shouldThrow<DrawingException> {
                DrawRectangle(Point(2, 2), Point(2, 2), createApplicationState(5)).execute()
            }
        }


    }
}