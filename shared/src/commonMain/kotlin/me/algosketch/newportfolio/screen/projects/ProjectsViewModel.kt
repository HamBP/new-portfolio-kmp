package me.algosketch.newportfolio.screen.projects

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ProjectsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    private val _event = Channel<ProjectsEvent>(Channel.BUFFERED)
    val event: Flow<ProjectsEvent> = _event.receiveAsFlow()

    fun onAction(action: ProjectsAction) {
        when (action) {
            is ProjectsAction.ClickLink -> _event.trySend(ProjectsEvent.OpenUrl(action.url))
        }
    }
}
