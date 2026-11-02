package me.algosketch.newportfolio.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.algosketch.newportfolio.navigation.Route

/** 홈 화면. ViewModel 과 이벤트 처리를 맡고, 그리기는 [HomeScreenContent] 에 맡긴다. */
@Composable
fun HomeScreen(
    onNavigate: (Route) -> Unit,
    viewModel: HomeViewModel = viewModel { HomeViewModel() },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(viewModel) {
        viewModel.event.collect { event ->
            when (event) {
                HomeEvent.NavigateToProjects -> onNavigate(Route.Projects)
                is HomeEvent.OpenUrl -> uriHandler.openUri(event.url)
            }
        }
    }

    HomeScreenContent(uiState = uiState, onAction = viewModel::onAction)
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HeroSection(strengths = uiState.strengths)
        FeaturedProjectsSection(projects = uiState.featuredProjects, onAction = onAction)
        CareerTimelineSection(
            timeline = uiState.timeline,
            selectedFilter = uiState.selectedTimelineFilter,
            onAction = onAction,
        )
        OtherActivitiesSection(
            blogPosts = uiState.blogPosts,
            openSourceContributions = uiState.openSourceContributions,
            onAction = onAction,
        )
    }
}
