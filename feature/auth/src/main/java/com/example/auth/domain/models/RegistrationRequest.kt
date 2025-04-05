package com.example.auth.domain.models

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("username")
    val username:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("passwordConfirmation")
    val passwordConfirmation:String

)
