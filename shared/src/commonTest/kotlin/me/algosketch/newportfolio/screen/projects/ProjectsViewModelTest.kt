package me.algosketch.newportfolio.screen.projects

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProjectsViewModelTest {

    @Test
    fun exposesAllProjects() {
        val viewModel = ProjectsViewModel()

        val names = viewModel.uiState.value.projects.map { it.name }
        assertEquals(listOf("불티", "쏙", "Catchy Tape"), names)
        assertTrue(viewModel.uiState.value.projects.all { it.achievements.isNotEmpty() })
    }

    @Test
    fun clickingLinkSendsOpenUrlEvent() = runTest {
        val viewModel = ProjectsViewModel()

        viewModel.onAction(ProjectsAction.ClickLink("https://github.com/HamBP"))

        assertEquals(ProjectsEvent.OpenUrl("https://github.com/HamBP"), viewModel.event.first())
    }
}
