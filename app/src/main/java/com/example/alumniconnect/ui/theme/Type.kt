package com.example.alumniconnect.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.alumniconnect.R

object AppFont {
    val RobotoFamily = FontFamily(
        Font(R.font.roboto_regular),
        Font(R.font.roboto_italic, style = FontStyle.Italic),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_medium_italic, FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_bold_italic, FontWeight.Bold, style = FontStyle.Italic)
    )
}

private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.RobotoFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.RobotoFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.RobotoFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.RobotoFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.RobotoFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.RobotoFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.RobotoFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.RobotoFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.RobotoFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.RobotoFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.RobotoFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.RobotoFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.RobotoFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.RobotoFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.RobotoFamily)
)