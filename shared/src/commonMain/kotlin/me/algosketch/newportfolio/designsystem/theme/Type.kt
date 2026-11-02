package me.algosketch.newportfolio.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import newportfolio.shared.generated.resources.Res
import newportfolio.shared.generated.resources.jetbrains_mono_bold
import newportfolio.shared.generated.resources.jetbrains_mono_regular
import newportfolio.shared.generated.resources.jetbrains_mono_semibold
import newportfolio.shared.generated.resources.pretendard_bold
import newportfolio.shared.generated.resources.pretendard_medium
import newportfolio.shared.generated.resources.pretendard_regular
import newportfolio.shared.generated.resources.pretendard_semibold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont

/** 본문용 Pretendard 와 기간·태그·수치용 JetBrains Mono. */
@Immutable
class PortfolioFonts(
    val sans: FontFamily,
    val mono: FontFamily,
)

internal val LocalPortfolioFonts = staticCompositionLocalOf<PortfolioFonts> {
    error("PortfolioTheme 안에서만 사용할 수 있습니다.")
}

/**
 * 번들한 폰트를 모두 내려받으면 [PortfolioFonts] 를, 그 전에는 null 을 돌려준다.
 * Canvas 렌더링이라 폰트가 늦게 도착하면 글리프가 비어 보이므로, 다 받은 뒤에 화면을 그린다.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun rememberPortfolioFonts(): PortfolioFonts? {
    val sans = listOf(
        preloadFont(Res.font.pretendard_regular, FontWeight.Normal),
        preloadFont(Res.font.pretendard_medium, FontWeight.Medium),
        preloadFont(Res.font.pretendard_semibold, FontWeight.SemiBold),
        preloadFont(Res.font.pretendard_bold, FontWeight.Bold),
    )
    val mono = listOf(
        preloadFont(Res.font.jetbrains_mono_regular, FontWeight.Normal),
        preloadFont(Res.font.jetbrains_mono_semibold, FontWeight.SemiBold),
        preloadFont(Res.font.jetbrains_mono_bold, FontWeight.Bold),
    )
    val sansFonts = sans.loadedOrNull() ?: return null
    val monoFonts = mono.loadedOrNull() ?: return null
    return PortfolioFonts(
        sans = FontFamily(sansFonts),
        mono = FontFamily(monoFonts),
    )
}

private fun List<State<Font?>>.loadedOrNull(): List<Font>? =
    map { it.value ?: return null }

/** CSS 처럼 줄 높이의 남는 공간을 글자 위아래에 똑같이 나눈다. */
private val CssLineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None,
)

/** CSS 기본 줄 높이(Tailwind preflight 의 1.5) */
const val DefaultLineHeight = 1.5f

object PortfolioTextStyle {
    @Composable
    fun sans(
        size: TextUnit,
        color: Color,
        weight: FontWeight = FontWeight.Normal,
        lineHeight: Float = DefaultLineHeight,
        letterSpacing: TextUnit = 0.sp,
    ): TextStyle = style(LocalPortfolioFonts.current.sans, size, color, weight, lineHeight, letterSpacing)

    @Composable
    fun mono(
        size: TextUnit,
        color: Color,
        weight: FontWeight = FontWeight.Normal,
        lineHeight: Float = DefaultLineHeight,
        letterSpacing: TextUnit = 0.sp,
    ): TextStyle = style(LocalPortfolioFonts.current.mono, size, color, weight, lineHeight, letterSpacing)

    private fun style(
        family: FontFamily,
        size: TextUnit,
        color: Color,
        weight: FontWeight,
        lineHeight: Float,
        letterSpacing: TextUnit,
    ) = TextStyle(
        fontFamily = family,
        fontSize = size,
        color = color,
        fontWeight = weight,
        lineHeight = lineHeight.em,
        letterSpacing = letterSpacing,
        lineHeightStyle = CssLineHeightStyle,
    )
}
