package dev.iaiabot.todo.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Teal700 = Color(0xFF018786)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val ProgressColor = Color(0xa1111111)

private val DarkColors = darkColors(
    primary = Purple200,
    secondary = Teal200,
)
private val LightColors = lightColors(
    primary = Purple500,
    primaryVariant = Teal700,
    secondary = Purple700,
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}

@Composable
@Preview
private fun MyThemeLightPreview() {
    MyTheme(darkTheme = false) {
        Card {
            Text("Hello Light World!")
        }
    }
}

@Composable
@Preview
private fun MyThemeDarkPreview() {
    MyTheme(darkTheme = true) {
        Card {
            Text("Hello Dark World!")
        }
    }
}
