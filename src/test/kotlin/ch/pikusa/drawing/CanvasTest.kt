package ch.pikusa.drawing

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.*
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.string.shouldHaveLength

class CanvasTest : StringSpec() {

    init {
        "Should render empty canvas correctly" {
            val testCanvas = Canvas(6, 4)

            val printout = testCanvas.render()

            printout shouldHaveSize 4
            printout[0] shouldHaveLength 6
            testCanvas should haveFilled(24, ' ')
        }

        "Should render non empty canvas correctly" {
            val testCanvas = Canvas(3, 3)
            testCanvas[Point(2, 3)] = 'x'
            testCanvas[Point(1, 3)] = 'x'

            val render = testCanvas.render()

            render shouldHaveSize 3
            render[0] shouldHaveLength 3
            render shouldBe listOf(
                "   ",
                "   ",
                "xx "
            )
        }

        "Canvas should have dimensions greater than 0" {
            shouldThrow<DrawingException> { Canvas(0, 10) }
            shouldThrow<DrawingException> { Canvas(10, 0) }
            shouldThrow<DrawingException> { Canvas(0, 0) }
        }

       "Should be able to draw point on the canvas" {
           val testCanvas = Canvas(3, 3)

           testCanvas[Point(2, 3)] = 'x'

           testCanvas.render() shouldBe listOf(
               "   ",
               "   ",
               " x "
           )
       }

        "Should print with edge" {
            val testCanvas = Canvas(3, 3)

            val printout = testCanvas.render(withFrame = true)

            printout shouldHaveSize 5
            printout[0] shouldHaveLength 5
            printout shouldBe listOf(
                "-----",
                "|   |",
                "|   |",
                "|   |",
                "-----"
            )
        }
    }
}
