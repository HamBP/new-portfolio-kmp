package me.algosketch.newportfolio.designsystem.theme

import androidx.compose.ui.graphics.Color

/**
 * Figma Make 디자인의 색 토큰. 흰 배경 기준 대비(WCAG)를 주석으로 남긴다.
 */
object PortfolioColors {
    val White = Color(0xFFFFFFFF)

    /** 제목. 17.4:1 */
    val Heading = Color(0xFF111827)

    /** 본문. 9.7:1 */
    val Body = Color(0xFF374151)

    /** 보조 본문. 7.7:1 */
    val Secondary = Color(0xFF4B5563)

    /** 흐린 텍스트. 4.6:1, 12px 이상에서만 쓴다. */
    val Muted = Color(0xFF6B7280)

    val Subtle = Color(0xFF9CA3AF)
    val Faint = Color(0xFFD1D5DB)

    val Border = Color(0xFFE5E7EB)
    val SurfaceAlt = Color(0xFFF9FAFB)
    val TagBackground = Color(0xFFF3F4F6)

    /** 불티 강조색 */
    val BooltiAccent = Color(0xFFFF6827)

    /** 흰 배경 위 텍스트용으로 어둡게 한 불티 강조색. 4.6:1 */
    val BooltiAccentText = Color(0xFFC05214)

    val SkyAccent = Color(0xFF0EA5E9)
    val SkyAccentText = Color(0xFF0369A1)

    val BadgeBackground = Color(0xFFFEF3C7)
    val BadgeBorder = Color(0xFFFDE68A)
    val BadgeText = Color(0xFF92400E)
}
