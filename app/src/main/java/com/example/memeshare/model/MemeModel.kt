package com.example.memeshare.model

import com.google.gson.annotations.SerializedName

data class MemeModel(
    @SerializedName("url")
    val memeUrl: String
)