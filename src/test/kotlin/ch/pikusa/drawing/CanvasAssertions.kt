package ch.pikusa.drawing

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult

fun haveFilled(numberOfFilledFields: Int, fillCharacter: Char = 'x') = Matcher<Canvas> { value ->
    val numberOfFieldsWithCharacter = value.render().joinToString("").count { it == fillCharacter }
    MatcherResult(
        numberOfFieldsWithCharacter == numberOfFilledFields,
        { "Canvas contains $numberOfFieldsWithCharacter '$fillCharacter' instead of $numberOfFilledFields" },
        { "" },
    )
}

fun createApplicationState(canvasWidth: Int, canvasHeight: Int = canvasWidth): ApplicationState {
    return ApplicationState(canvas = Canvas(canvasWidth, canvasHeight))
}