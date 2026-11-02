package me.algosketch.newportfolio.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import me.algosketch.newportfolio.navigation.Route
import me.algosketch.newportfolio.navigation.rememberPortfolioNavigator
import me.algosketch.newportfolio.screen.home.HomeScreen
import me.algosketch.newportfolio.screen.projects.ProjectsScreen

/** Navigation 배경이 바뀌는 스크롤 거리 */
private val NavigationScrolledThreshold = 20.dp

/**
 * 앱 뼈대. 페이지 전체가 한 번에 스크롤되고, 그 위에 Navigation 이 고정된다.
 * 경로가 바뀌면 스크롤을 맨 위로 되돌린다.
 */
@Composable
fun PortfolioApp() {
    val navigator = rememberPortfolioNavigator()
    val scrollState = rememberScrollState()
    val thresholdPx = with(LocalDensity.current) { NavigationScrolledThreshold.toPx() }
    val scrolled by remember(thresholdPx) { derivedStateOf { scrollState.value > thresholdPx } }

    LaunchedEffect(navigator.currentRoute) {
        scrollState.scrollTo(0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SelectionContainer {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            ) {
                when (navigator.currentRoute) {
                    Route.Home -> HomeScreen(onNavigate = navigator::navigate)
                    Route.Projects -> ProjectsScreen()
                }
                Footer()
            }
        }

        NavigationBar(
            currentRoute = navigator.currentRoute,
            scrolled = scrolled,
            onNavigate = navigator::navigate,
        )
    }
}
