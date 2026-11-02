package me.algosketch.newportfolio.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.algosketch.newportfolio.designsystem.component.IconTextLink
import me.algosketch.newportfolio.designsystem.component.SectionTitle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme
import me.algosketch.newportfolio.navigation.Route

/** 홈 화면. 섹션은 이후 작업에서 채운다. */
@Composable
fun HomeScreen(onNavigate: (Route) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = PortfolioTheme.windowSize.height),
        contentAlignment = Alignment.Center,
    ) {
        SectionTitle("홈")
        IconTextLink(
            text = "프로젝트 모두 보기",
            onClick = { onNavigate(Route.Projects) },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
