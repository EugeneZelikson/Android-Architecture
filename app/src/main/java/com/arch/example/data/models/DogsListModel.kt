package com.arch.example.data.models

import com.google.gson.annotations.SerializedName

data class DogsListModel(
    @SerializedName("message") var images: MutableList<String>,
    var status: String
)