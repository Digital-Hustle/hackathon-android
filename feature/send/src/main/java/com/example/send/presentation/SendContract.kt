package com.example.send.presentation

import android.net.Uri
import com.example.core.presentation.ViewEvent
import com.example.core.presentation.ViewSideEffect
import com.example.core.presentation.ViewState

class SendContract {

    sealed class Event : ViewEvent {
        data object ToProfileButtonClicked : Event()
        data object ToAuthButtonClicked : Event()
        data object OpenManualButtonClicked : Event()
        data object ClosedManualButtonClicked : Event()

        data class OnPowerValueChanged(val newValue:String):Event()
        data class OnVoltageValueChanged(val newValue: String):Event()
        data object BackButtonClicked : Event()
        data object SendButtonClicked : Event()

        data class OnFileSelected(val uri: Uri, val extension: String) : Event()
        data object OnIncorrectFileFormat : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isAuthorized: Boolean = false,
        val isUploadError: Boolean = false,
        val isSuccessfulUpload: Boolean = false,
        val error: String? = null,
        val savedFileName: String? = null,
        val isFileSaved: Boolean = false,
        val isManualOpened: Boolean = false,
        val isVoltageLevelSelectorMenuOpened: Boolean = false,
        val maxPower: String = "",
        val voltageLevel: VoltageLevel = VoltageLevel.BH
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object ToAuth : Navigation()
            data object ToMain : Navigation()
            data object ToProfile : Navigation()
        }
    }

}

enum class VoltageLevel(val value: String) {
    BH("ВН"), CH1("СН1"), CH2("СН2"), HH("НН");

    companion object {
        fun getByValue(extension: String): VoltageLevel {
            return entries.firstOrNull { it.value.equals(extension, ignoreCase = true) }
                ?: BH
        }

        fun toListOfValues():List<String>{
            return entries.map { it.value }
        }
    }

}