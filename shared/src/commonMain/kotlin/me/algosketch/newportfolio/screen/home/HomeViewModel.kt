package me.algosketch.newportfolio.screen.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _event = Channel<HomeEvent>(Channel.BUFFERED)
    val event: Flow<HomeEvent> = _event.receiveAsFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.SelectTimelineFilter -> _uiState.update { it.copy(selectedTimelineFilter = action.filter) }
            HomeAction.ClickViewAllProjects -> _event.trySend(HomeEvent.NavigateToProjects)
            is HomeAction.ClickLink -> _event.trySend(HomeEvent.OpenUrl(action.url))
        }
    }
}
