package com.example.mycar.network

import com.google.gson.annotations.SerializedName

data class MyCarLogo (
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
)