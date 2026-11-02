package me.algosketch.newportfolio.screen.projects

import androidx.compose.runtime.Immutable
import me.algosketch.newportfolio.data.ProjectsContent
import me.algosketch.newportfolio.model.Project

@Immutable
data class ProjectsUiState(
    val projects: List<Project> = ProjectsContent.projects,
)

sealed interface ProjectsAction {
    data class ClickLink(val url: String) : ProjectsAction
}

sealed interface ProjectsEvent {
    data class OpenUrl(val url: String) : ProjectsEvent
}
