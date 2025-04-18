package com.example.profile.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.core.domain.UserStorage
import com.example.core.mLog
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.CoroutinesErrorHandler
import com.example.network.ApiResponse
import com.example.profile.data.paging.HistoryPagingSource
import com.example.profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val userStorage: UserStorage
) : BaseViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {

    init {
        viewModelScope.launch {
            userStorage.getUsername().filter { it != null }.collect {
                mLog("state", it.toString())
                setState { copy(userName = it) }
            }
        }
    }


    override fun setInitialState(): ProfileContract.State = ProfileContract.State(
        isLoading = true
    )

    override fun handleEvents(event: ProfileContract.Event) {
        when (event) {
            ProfileContract.Event.BackButtonClicked -> setEffect { ProfileContract.Effect.Navigation.ToMain }
            ProfileContract.Event.OnLoading -> setState {
                copy(
                    isLoading = true,
                    error = null,
                    isEmptyResults = false
                )
            }

            is ProfileContract.Event.OnError -> setState {
                copy(
                    isLoading = false,
                    error = event.error,
                    isEmptyResults = false
                )
            }

            is ProfileContract.Event.OnDataLoaded -> setState {
                copy(
                    isLoading = false,
                    isEmptyResults = false
//                    isEmptyResults = event.isEmptyResults
                )
            }

            ProfileContract.Event.OnEmptyDataLoaded -> setState {
                copy(
                    isLoading = false,
                    isEmptyResults = true
                )
            }
            ProfileContract.Event.ToChatButtonClicked -> setEffect { ProfileContract.Effect.Navigation.ToChat(1) }
            ProfileContract.Event.OnDismissMenu -> setState { copy(showMenu = false) }
            ProfileContract.Event.OnMenuBottomClicked -> setState { copy(showMenu = true) }
            ProfileContract.Event.Logout -> setEffect { ProfileContract.Effect.Logout }
            ProfileContract.Event.OpenHistoryButtonClicked -> {
                setState { copy(isHistoryOpened = true, historyPagingFlow = getHistoryPager()) }
            }

            ProfileContract.Event.ClosedHistoryButtonClicked -> {
                setState { copy(isHistoryOpened = false, historyPagingFlow = null) }

            }
        }
    }


    private fun getFile(fileId: Int) {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = {
                repository.getFile(fileId)
            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
//                        setState { copy(loginResponse = response.data, error = null) }
//                        viewModelScope.launch {
//                            setId(response.data.id)
//                            setUserName(response.data.username)
//                        }
//                        setEffect {
//                            ProfileContract.Effect.LoginWasLoaded(
//                                response.data.token,
//                                response.data.refreshToken
//                            )
//                        }
//                        setEffect { ProfileContract.Effect.Navigation.toMain }

                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
                }
            }
        )
    }

    private fun getHistoryPager() = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {
            HistoryPagingSource(
                repository,
                { event -> setEvent(event) })
        }
    ).flow.cachedIn(viewModelScope)


}