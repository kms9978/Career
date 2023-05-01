package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class CodenaryData(
    @SerializedName("preview")
    val title: String,
    val logo: String,
    val info: String,
    val date: String
    )