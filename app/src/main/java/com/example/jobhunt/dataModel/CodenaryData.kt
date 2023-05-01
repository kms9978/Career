package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class CodenaryData(
    @SerializedName("preview")// JSON에서 title 필드 대신 preview 필드로 변환되어 매핑.
    val title: String,
    val logo: String,
    val info: String,
    val date: String
    )