package me.algosketch.newportfolio.screen.home

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.algosketch.newportfolio.model.TimelineFilter
import me.algosketch.newportfolio.model.TimelineType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HomeViewModelTest {

    @Test
    fun timelineFilterIsAllByDefault() {
        val viewModel = HomeViewModel()

        assertEquals(TimelineFilter.All, viewModel.uiState.value.selectedTimelineFilter)
        assertTrue(viewModel.uiState.value.timeline.all { TimelineFilter.All.matches(it) })
    }

    @Test
    fun selectingFilterUpdatesState() {
        val viewModel = HomeViewModel()

        viewModel.onAction(HomeAction.SelectTimelineFilter(TimelineFilter.Career))

        val state = viewModel.uiState.value
        assertEquals(TimelineFilter.Career, state.selectedTimelineFilter)
        val matched = state.timeline.filter { state.selectedTimelineFilter.matches(it) }
        assertTrue(matched.isNotEmpty())
        assertTrue(matched.all { it.type == TimelineType.Career })
    }

    @Test
    fun clickingViewAllProjectsSendsNavigateEvent() = runTest {
        val viewModel = HomeViewModel()

        viewModel.onAction(HomeAction.ClickViewAllProjects)

        assertEquals(HomeEvent.NavigateToProjects, viewModel.event.first())
    }

    @Test
    fun clickingLinkSendsOpenUrlEvent() = runTest {
        val viewModel = HomeViewModel()

        viewModel.onAction(HomeAction.ClickLink("https://example.com"))

        assertEquals(HomeEvent.OpenUrl("https://example.com"), viewModel.event.first())
    }
}
