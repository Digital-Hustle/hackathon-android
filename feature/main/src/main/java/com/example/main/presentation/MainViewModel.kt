package com.example.main.presentation

import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.example.core.domain.UserStorage
import com.example.core.mLog
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.CoroutinesErrorHandler
import com.example.network.ApiResponse
import com.example.network.presentation.FileMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    private val repository: AuthRepository,
    private val userStorage: UserStorage,
    private val fileMapper: FileMapper
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    init {
        viewModelScope.launch {
            userStorage.getUsername().filter { it != null }.collect{
                setState {
                    copy(isAuthorized = true)
                }
            }
        }
    }

    override fun setInitialState(): MainContract.State = MainContract.State(


    )

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.LoadTemplatesButtonClicked ->{
                    fileMapper.saveTemplate()
            }
            MainContract.Event.ToAuthButtonClicked ->{


                setEffect { MainContract.Effect.Navigation.ToAuth }}
            MainContract.Event.ToProfileButtonClicked -> {
                setEffect { MainContract.Effect.Navigation.ToProfile }
            }
            MainContract.Event.ToSendButtonClicked -> {
                setEffect { MainContract.Effect.Navigation.ToSend }
            }
        }
    }


//    private fun login() {
//        baseRequest(
//            errorHandler = object : CoroutinesErrorHandler {
//                override fun onError(message: String) {
//                    setState { copy(error = message) }
////                    setEffect { CurrencyEffect.ShowError(message) }
//                }
//            },
//            request = {
//                repository.login(LoginRequest(viewState.value.login, viewState.value.password))
//            },
//            onSuccess = { response ->
//                when (response) {
//                    is ApiResponse.Success -> {
//                        viewModelScope.launch {
//                            setId(response.data.id)
//                            setUserName(response.data.username)
//                        }
////                        setState { copy(loginResponse = response.data, error = null) }
//                        setEffect {
//                            MainContract.Effect.LoginWasLoaded(
//                                response.data.token,
//                                response.data.refreshToken
//                            )
//                        }
//                        setEffect { MainContract.Effect.Navigation.toMain }
//                    }
//
//                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
//                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
//                }
//            }
//        )
//    }


//    private fun loginWithGoogle() {
//        baseRequest(
//            errorHandler = object : CoroutinesErrorHandler {
//                override fun onError(message: String) {
//                    setState { copy(error = message) }
////                    setEffect { CurrencyEffect.ShowError(message) }
//                }
//            },
//            request = {
//                repository.loginWithGoogle()
//            },
//            onSuccess = { response ->
//                when (response) {
//                    is ApiResponse.Success -> {
////                        setState { copy(loginResponse = response.data, error = null) }
//                        viewModelScope.launch {
//                            setId(response.data.id)
//                            setUserName(response.data.username)
//                        }
//                        setEffect {
//                            MainContract.Effect.LoginWasLoaded(
//                                response.data.token,
//                                response.data.refreshToken
//                            )
//                        }
//                        setEffect { MainContract.Effect.Navigation.toMain }
//
//                    }
//
//                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
//                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
//                }
//            }
//        )
//    }
//
//    private fun registration() {
//        baseRequest(
//            errorHandler = object : CoroutinesErrorHandler {
//                override fun onError(message: String) {
//                    setState { copy(error = message) }
////                    setEffect { CurrencyEffect.ShowError(message) }
//                }
//            },
//            request = {
//                repository.registration(
//                    RegistrationRequest(
//                        viewState.value.login,
//                        viewState.value.password,
//                        viewState.value.passwordConfirmation
//                    )
//                )
//            },
//            onSuccess = { response ->
//                when (response) {
//                    is ApiResponse.Success -> {
//
////                        setState { copy(registrationResponse = response.data, error = null) }
//                        setEffect { MainContract.Effect.RegistrationWasLoaded }
//
//                    }
//
//                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
//                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
//                }
//            }
//        )
//    }
//
//    private suspend fun setId(id: Int) {
//        repository.setId(id)
//    }
//
//    private suspend fun setUserName(name: String) {
//        repository.setUserName(name)
//    }
}