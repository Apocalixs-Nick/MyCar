package com.example.mycar.network.car

import com.google.gson.annotations.SerializedName

data class MyCarInfo(
    @SerializedName("MAKER") val maker: String,
    @SerializedName("MODEL") val model: String
)