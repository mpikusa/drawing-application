package ch.pikusa.drawing

import ch.pikusa.drawing.commands.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.engine.test.logging.error
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs

class CommandParserTest : StringSpec() {
    init {

        forAll(
            row("Q"),
            row("q"),
            row("q "),
            row("Q fsdfsdf"),
        ) { userCommand ->
            "Should parse quit command correctly $userCommand" {
                val testApplicationState = ApplicationState()

                val result = CommandParser(testApplicationState).parse(userCommand)

                result.shouldBeInstanceOf<Quit>()
                result.applicationState shouldBe testApplicationState
            }
        }

        "Should throw exception if command is not supported" {
            shouldThrow<DrawingException> { CommandParser(ApplicationState()).parse("WRONGCOMMAND") }
        }

        forAll(
            row("C 4 6"),
            row("c 4 6"),
            row("C 4 6 2"),
        ) { userCommand ->
            "Should correctly parse CreateCanvas command $userCommand" {
                val applicationState = ApplicationState()

                val result = CommandParser(applicationState).parse(userCommand)

                result.shouldBeInstanceOf<CreateCanvas>()
                result.height shouldBe 6
                result.width shouldBe 4
            }
        }

        forAll(
            row("C"),
            row("c -5"),
            row("C er e"),
        ) { userCommand ->
            "Should throw exception create canvas command is incorrect: $userCommand" {
                shouldThrow<DrawingException> {
                    CommandParser(ApplicationState()).parse(userCommand)
                }
            }
        }

        forAll(
            row("L 1 2 1 4"),
            row("l 1 2 1 4"),
            row("l 1  2 1  4 df"),
        ) { userCommand ->
            "Should correctly parse DrawLine command with lower case" {
                val applicationState = createApplicationState(5)
                val expected = DrawLine(Point(1, 1), Point(1, 4), applicationState)

                val result = CommandParser(applicationState).parse(userCommand)

                result.shouldBeInstanceOf<DrawLine>()

                result.p1 shouldBe Point(1, 2)
                result.p2 shouldBe Point(1, 4)
                result.applicationState shouldBe applicationState
            }
        }

        forAll(
            row("L 0 0 0 4"),
            row("L r 3 0 5"),
            row("L -1 -1 -1 -4"),
            row("L 1 1 1"),
            row("L 4 1"),
            row("L"),
        ) { userCommand ->
            "Should throw exception if Draw Line command is incorrect $userCommand" {

                shouldThrow<DrawingException> {
                    CommandParser(createApplicationState(5)).parse(userCommand)
                }
            }
        }

        forAll(
            row("R 1 2 5 4"),
            row("r 1 2 5 4"),
            row("r 1  2 5  4 df"),
        ) { userCommand ->
            "Should correctly parse DrawRectangle command $userCommand" {
                val applicationState = createApplicationState(5)

                val command = CommandParser(applicationState).parse(userCommand)

                command.shouldBeInstanceOf<DrawRectangle>()
                command.topLeft shouldBe Point(1, 2)
                command.bottomRight shouldBe Point(5, 4)
            }
        }

        forAll(
            row("R 0 0 4 4"),
            row("R -1 -1 -4 -4"),
            row("R 1 1 5"),
            row("R 4 1"),
            row("R"),
        ) { userCommand ->
            "Should throw exception if Draw Rectangle command is incorrect $userCommand" {

                shouldThrow<DrawingException> {
                    CommandParser(createApplicationState(5)).parse(userCommand)
                }
            }
        }

        forAll(
            row("B 2 1 g"),
            row("b 2 1 g"),
            row("b 2  1 g  f"),
        ) { userCommand ->
            "Should correctly parse Fill command $userCommand" {
                val applicationState = createApplicationState(5)

                val command = CommandParser(applicationState).parse(userCommand)

                command.shouldBeInstanceOf<Fill>()
                command.startingPoint shouldBe Point(2, 1)
                command.color shouldBe 'g'
            }
        }

        forAll(
            row("B o"),
            row("B -1 -1 o"),
            row("B 0 1 ?"),
            row("b r 1 f"),
            row("b"),
        ) { userCommand ->
            "Should throw DrawingException if Fill command is incorrect $userCommand" {
                shouldThrow<DrawingException> {
                    CommandParser(createApplicationState(5)).parse(userCommand)
                }
            }
        }

        "should parse Fill command from the example" {
            val applicationState = createApplicationState(20)

            val command = CommandParser(applicationState).parse("B 10 3 o")

            command.shouldBeInstanceOf<Fill>()
            command.startingPoint shouldBe Point(10, 3)
            command.color shouldBe 'o'
        }

        "Should parse fill command with space as a color" {
            val applicationState = createApplicationState(20)

            val command = CommandParser(applicationState).parse("B 5 2  ")

            command.shouldBeInstanceOf<Fill>()
            command.startingPoint shouldBe Point(5, 2)
            command.color shouldBe ' '
        }

    }
}