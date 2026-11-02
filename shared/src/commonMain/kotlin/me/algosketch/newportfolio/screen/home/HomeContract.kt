package me.algosketch.newportfolio.screen.home

import androidx.compose.runtime.Immutable
import me.algosketch.newportfolio.data.HomeContent
import me.algosketch.newportfolio.model.Contribution
import me.algosketch.newportfolio.model.FeaturedProject
import me.algosketch.newportfolio.model.Strength
import me.algosketch.newportfolio.model.TimelineFilter
import me.algosketch.newportfolio.model.TimelineItem

@Immutable
data class HomeUiState(
    val strengths: List<Strength> = HomeContent.strengths,
    val featuredProjects: List<FeaturedProject> = HomeContent.featuredProjects,
    val timeline: List<TimelineItem> = HomeContent.timeline,
    val selectedTimelineFilter: TimelineFilter = TimelineFilter.All,
    val blogPosts: List<Contribution> = HomeContent.blogPosts,
    val openSourceContributions: List<Contribution> = HomeContent.openSourceContributions,
)

sealed interface HomeAction {
    data class SelectTimelineFilter(val filter: TimelineFilter) : HomeAction
    data object ClickViewAllProjects : HomeAction
    data class ClickLink(val url: String) : HomeAction
}

sealed interface HomeEvent {
    data object NavigateToProjects : HomeEvent
    data class OpenUrl(val url: String) : HomeEvent
}
