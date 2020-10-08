package com.arch.example.data.models

import com.google.gson.annotations.SerializedName

data class DogsRandomModel(
    @SerializedName("message")
    var imageUrl: String,

    @SerializedName("status")
    var status: String
)