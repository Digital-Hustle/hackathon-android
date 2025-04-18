package com.example.send.presentation

import android.net.Uri
import androidx.annotation.RequiresExtension
import androidx.lifecycle.viewModelScope
import com.example.core.domain.UserStorage
import com.example.core.mLog
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.CoroutinesErrorHandler
import com.example.network.ApiResponse
import com.example.network.presentation.FileMapper
import com.example.send.domain.repository.SendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SendViewModel @Inject constructor(
    private val repository: SendRepository,
    private val fileMapper: FileMapper,
    userStorage: UserStorage
) : BaseViewModel<SendContract.Event, SendContract.State, SendContract.Effect>() {

    init {
        viewModelScope.launch {
            userStorage.getUsername().filter { it != null }.collect {

                setState {
                    copy(isAuthorized = true)
                }
            }
        }
    }


    override fun setInitialState(): SendContract.State = SendContract.State()

    override fun handleEvents(event: SendContract.Event) {
        when (event) {
            SendContract.Event.BackButtonClicked -> setEffect { SendContract.Effect.Navigation.ToMain }
            SendContract.Event.SendButtonClicked -> {}
            SendContract.Event.ToAuthButtonClicked -> setEffect { SendContract.Effect.Navigation.ToAuth }
            SendContract.Event.ToProfileButtonClicked -> setEffect { SendContract.Effect.Navigation.ToProfile }
            SendContract.Event.OnIncorrectFileFormat -> setState {
                copy(
                    isUploadError = true,
                    isSuccessfulUpload = false
                )
            }

            is SendContract.Event.OnFileSelected -> {
                mLog("time", event.uri.toString())
                setState { copy(isUploadError = false, isSuccessfulUpload = true) }
                uploadFile(event.uri,event.extension)
            }
            SendContract.Event.OpenManualButtonClicked -> {
                setState { copy(isManualOpened = true,) }
            }

            SendContract.Event.ClosedManualButtonClicked -> {
                setState { copy(isManualOpened = false,) }

            }

            is SendContract.Event.OnPowerValueChanged -> {
                setState { copy(maxPower =  event.newValue) }
            }

            is SendContract.Event.OnVoltageValueChanged -> {
                setState { copy(voltageLevel = VoltageLevel.getByValue(event.newValue)) }
            }
        }
    }


    private fun uploadFile(uri: Uri, extension: String) {

        viewModelScope.launch {
            delay(1500)
            setState { copy(isFileSaved = true, savedFileName = "electricity_report_060420251055.xlsx", isUploadError = false, isSuccessfulUpload = false) }

        }

        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
                }
            },
            request = {


                repository.uploadFile(fileMapper.prepareFileForUpload(uri, extension))
            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val fileName = response.data.fileName
                        val file = fileMapper.saveMultipartToFile(
                            fileName,
                            response.data.file
                        )
                        val success = fileMapper.saveFileToInternalStorage(file, fileName)
                        if (success) {
                            setState { copy(isFileSaved = true, savedFileName = fileName, isUploadError = false) }
                        } else {
                            setState { copy(isFileSaved = false, savedFileName = null, isUploadError = false) }

                        }
                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState { copy(error = null, isLoading = true) }
                }
            }
        )
    }
}