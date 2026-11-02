package me.algosketch.newportfolio.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.unit.sp

private val ColorScheme = lightColorScheme(
    primary = PortfolioColors.Heading,
    onPrimary = PortfolioColors.White,
    secondary = PortfolioColors.TagBackground,
    onSecondary = PortfolioColors.Heading,
    background = PortfolioColors.White,
    onBackground = PortfolioColors.Heading,
    surface = PortfolioColors.White,
    onSurface = PortfolioColors.Heading,
    surfaceVariant = PortfolioColors.SurfaceAlt,
    onSurfaceVariant = PortfolioColors.Muted,
    outline = PortfolioColors.Border,
)

private val SelectionColors = TextSelectionColors(
    handleColor = PortfolioColors.Heading,
    backgroundColor = PortfolioColors.Heading.copy(alpha = 0.15f),
)

/**
 * 포트폴리오 테마. 번들 폰트를 다 받은 뒤에 [content] 를 그린다.
 *
 * Pretendard 를 [androidx.compose.ui.text.font.FontFamily.Resolver.preload] 로 미리 등록해
 * JetBrains Mono 에 없는 한글 글리프가 Pretendard 로 대체되게 한다.
 */
@Composable
fun PortfolioTheme(content: @Composable () -> Unit) {
    val fonts = rememberPortfolioFonts()
    val resolver = LocalFontFamilyResolver.current
    var fallbackRegistered by remember { mutableStateOf(false) }

    LaunchedEffect(fonts) {
        if (fonts != null) {
            resolver.preload(fonts.sans)
            fallbackRegistered = true
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.White),
    ) {
        if (fonts == null || !fallbackRegistered) return@BoxWithConstraints

        CompositionLocalProvider(
            LocalWindowSize provides WindowSize(maxWidth, maxHeight),
            LocalPortfolioFonts provides fonts,
            LocalTextSelectionColors provides SelectionColors,
        ) {
            MaterialTheme(colorScheme = ColorScheme) {
                CompositionLocalProvider(
                    LocalTextStyle provides PortfolioTextStyle.sans(size = 16.sp, color = PortfolioColors.Heading),
                    content = content,
                )
            }
        }
    }
}

object PortfolioTheme {
    val windowSize: WindowSize
        @Composable get() = LocalWindowSize.current

    val fonts: PortfolioFonts
        @Composable get() = LocalPortfolioFonts.current
}
