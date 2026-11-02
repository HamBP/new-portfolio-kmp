package me.algosketch.newportfolio.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle

private val TagShape = RoundedCornerShape(4.dp)

enum class TechTagSize {
    /** 카드·타임라인용. 11px, 테두리 없음 */
    Small,

    /** 프로젝트 화면용. 12px, 회색 테두리 */
    Medium,
}

/** 기술 스택 태그 (예: `Compose`, `Hilt`) */
@Composable
fun TechTag(
    text: String,
    modifier: Modifier = Modifier,
    size: TechTagSize = TechTagSize.Small,
) {
    when (size) {
        TechTagSize.Small -> Text(
            text = text,
            modifier = modifier
                .background(PortfolioColors.TagBackground, TagShape)
                .padding(horizontal = 9.dp, vertical = 2.dp),
            style = PortfolioTextStyle.mono(size = 11.sp, color = PortfolioColors.Muted),
        )

        TechTagSize.Medium -> Text(
            text = text,
            modifier = modifier
                .background(PortfolioColors.TagBackground, TagShape)
                .border(1.dp, PortfolioColors.Faint, TagShape)
                .padding(horizontal = 11.dp, vertical = 4.dp),
            style = PortfolioTextStyle.mono(size = 12.sp, color = PortfolioColors.Secondary),
        )
    }
}
