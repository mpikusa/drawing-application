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

fun createApplicationState(width: Int, height: Int = width): ApplicationState {
    return ApplicationState(canvas = Canvas(width, height))
}