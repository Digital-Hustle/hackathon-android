package com.example.main.presentation

import com.example.core.presentation.ViewEvent
import com.example.core.presentation.ViewSideEffect
import com.example.core.presentation.ViewState

class MainContract {

    sealed class Event : ViewEvent {
        data object ToSendButtonClicked : Event()
        data object ToProfileButtonClicked : Event()
        data object LoadTemplatesButtonClicked : Event()
        data object ToAuthButtonClicked : Event()
    }

    data class State(
        val isAuthorized: Boolean = false,
        val error: String? = null,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object ToAuth : Navigation()
            data object ToSend : Navigation()
            data object ToProfile : Navigation()
        }
    }

}