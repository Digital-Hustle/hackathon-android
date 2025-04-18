package com.example.profile.presentation

import androidx.paging.PagingData
import com.example.core.presentation.ViewEvent
import com.example.core.presentation.ViewSideEffect
import com.example.core.presentation.ViewState
import com.example.profile.presentation.model.HistoryReport
import kotlinx.coroutines.flow.Flow

class ProfileContract {

    sealed class Event : ViewEvent {
        data object BackButtonClicked:Event()
        data object ToChatButtonClicked:Event()
        data object OpenHistoryButtonClicked:Event()
        data object ClosedHistoryButtonClicked:Event()



        data object OnLoading : Event()
        data class OnError(val error: String) : Event()
        data object OnDataLoaded : Event()
        data object OnEmptyDataLoaded:Event()
        data object OnMenuBottomClicked:Event()
        data object OnDismissMenu:Event()
        data object Logout:Event()
    }

    data class State(
        val userName:String? = null,
        val historyPagingFlow : Flow<PagingData<HistoryReport>>? = null ,
        val error: String? = null,
        val isLoading: Boolean = false,
        val isEmptyResults:Boolean = false,
        val isHistoryOpened:Boolean = false,
        val showMenu:Boolean = false

    ) : ViewState

    sealed class Effect : ViewSideEffect {
        object Logout:Effect()


        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            data class ToChat(val chatId:Int):Navigation()
        }
    }

}