package me.algosketch.newportfolio.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.algosketch.newportfolio.designsystem.theme.PortfolioDimens

/**
 * `max-width: 1000px; margin: 0 auto; padding: 0 24px` 컨테이너.
 * [modifier] 는 바깥(전체 폭) 영역에 적용된다.
 */
@Composable
fun ContentContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = PortfolioDimens.ContentMaxWidth)
                .fillMaxWidth()
                .padding(horizontal = PortfolioDimens.ContentHorizontalPadding),
            content = content,
        )
    }
}
