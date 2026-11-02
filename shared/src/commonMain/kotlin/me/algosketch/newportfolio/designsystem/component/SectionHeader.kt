package me.algosketch.newportfolio.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme

/** 섹션 제목 위의 작은 영문 라벨 (예: `SIDE PROJECTS`). 12px 모노, 대문자, 자간 2px. */
@Composable
fun SectionEyebrow(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = PortfolioColors.Subtle,
) {
    Text(
        text = text.uppercase(),
        modifier = modifier,
        style = PortfolioTextStyle.mono(size = 12.sp, color = color, letterSpacing = 2.sp),
    )
}

/** 섹션 제목. `clamp(26px, 4vw, 36px)`, 굵게, 자간 -1px. */
@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = PortfolioTextStyle.sans(
            size = PortfolioTheme.windowSize.fluidSp(min = 26f, vw = 4f, max = 36f),
            color = PortfolioColors.Heading,
            weight = FontWeight.Bold,
            letterSpacing = (-1).sp,
        ),
    )
}
