package me.algosketch.newportfolio.screen.projects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.algosketch.newportfolio.designsystem.component.SectionTitle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme

/** 프로젝트 화면. 내용은 이후 작업에서 채운다. */
@Composable
fun ProjectsScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = PortfolioTheme.windowSize.height),
        contentAlignment = Alignment.Center,
    ) {
        SectionTitle("프로젝트")
    }
}
