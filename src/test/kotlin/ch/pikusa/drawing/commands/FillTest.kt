package ch.pikusa.drawing.commands

import ch.pikusa.drawing.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe


class FillTest: StringSpec() {
    init {

        "Should throw exception if point to fill is beyond the canvas" {
            shouldThrow<DrawingException> {
                Fill(Point(5, 5), 'o', createApplicationState(4)).execute()
            }
        }

        "Should fill entire empty canvas" {
            val applicationState = createApplicationState(3)

            Fill(Point(1, 1), 'o', applicationState).execute()

            applicationState.canvas!! should haveFilled(9, 'o')
        }

        "Should fill line only if fill point is a part of it" {
            val applicationState = createApplicationState(5)
            DrawLine(Point(2, 1), Point(5, 1), applicationState).execute()

            Fill(Point(2, 1), 'u', applicationState).execute()

            applicationState.canvas!! should haveFilled(4, 'u')
        }

        "Should fill area limited by a rectangle without filling its edges if point is within the rectangle" {
            val applicationState = createApplicationState(5)
            DrawRectangle(Point(2, 2), Point(5, 5), applicationState).execute()

            Fill(Point(3, 3), 'o', applicationState).execute()

            applicationState.canvas!!.render() shouldBe listOf(
                "     ",
                " xxxx",
                " xoox",
                " xoox",
                " xxxx",
            )
        }

        "Should not fill inside of the rectangle if fill point is outside of rectangle" {
            val applicationState = createApplicationState(5)
            DrawRectangle(Point(2, 2), Point(5, 5), applicationState).execute()

            Fill(Point(1, 1), 'o', applicationState).execute()

            applicationState.canvas!!.render() shouldBe listOf(
                "ooooo",
                "oxxxx",
                "ox  x",
                "ox  x",
                "oxxxx"
            )
        }
    }
}