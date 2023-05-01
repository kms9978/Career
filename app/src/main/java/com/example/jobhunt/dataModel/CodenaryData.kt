package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class CodenaryData(
    @SerializedName("preview")
    val preview: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("info")
    val info: String?,
    @SerializedName("date")
    val date: String?
    )