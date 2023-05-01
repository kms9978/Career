package com.example.jobhunt.dataModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CodenaryData(
    @SerializedName("preview")// JSON에서 title 필드 대신 preview 필드로 변환되어 매핑.
    val title: String?,
    val logo: String?,
    val info: String?,
    val date: String?
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(logo)
        parcel.writeString(info)
        parcel.writeString(date)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CodenaryData> {
        override fun createFromParcel(parcel: Parcel): CodenaryData {
            return CodenaryData(parcel)
        }

        override fun newArray(size: Int): Array<CodenaryData?> {
            return arrayOfNulls(size)
        }
    }
}