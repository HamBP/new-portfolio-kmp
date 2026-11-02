package me.algosketch.newportfolio.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/** 브라우저 창 크기. CSS 의 `vw`·`vh`·`@media` 를 대신한다. */
@Immutable
data class WindowSize(val width: Dp, val height: Dp) {
    /** `@media (max-width: 700px)` */
    val isCompact: Boolean get() = width <= PortfolioDimens.CompactBreakpoint

    /** CSS `clamp(min, {vw}vw, max)` 의 글자 크기 버전 */
    fun fluidSp(min: Float, vw: Float, max: Float): TextUnit =
        (width.value * vw / 100f).coerceIn(min, max).sp

    companion object {
        fun from(size: DpSize) = WindowSize(size.width, size.height)
    }
}

internal val LocalWindowSize = staticCompositionLocalOf { WindowSize(Dp.Unspecified, Dp.Unspecified) }
