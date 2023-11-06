package com.example.jobhunt.DataModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class BoardData (
    @SerializedName("board_id")
    val board_id : Long,
    @SerializedName("content")
    val content : String,
    @SerializedName("preview")
    val preview : String,
    @SerializedName("subject")
    val subject : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("writer")
    val writer : String

): Parcelable {// Parcelable 인터페이스를 구현하는 BoardData 클래스 선언

    // Parcel에서 데이터를 읽어와 객체를 생성하는 생성자
    constructor(parcel: Parcel) : this(
        parcel.readLong()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )
    // Parcel 객체에 객체를 쓰는 함수
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(board_id)
        parcel.writeString(content)
        parcel.writeString(preview)
        parcel.writeString(subject)
        parcel.writeString(title)
        parcel.writeString(writer)

    }

    // 필수 메서드인데, 별도의 적용이 필요 없기 때문에 0을 반환
    override fun describeContents(): Int {
        return 0
    }

    // Parcelable 인터페이스를 구현하는 객체 생성기를 제공하기 위한 companion object
    companion object CREATOR : Parcelable.Creator<BoardData> {
        // Parcel에서 데이터를 읽어와 객체를 생성하는 createFromParcel 메서드를 구현
        override fun createFromParcel(parcel: Parcel): BoardData {
            return BoardData(parcel)
        }

        // 지정된 크기의 배열을 만들고 null로 초기화하는 newArray 메서드를 구현
        override fun newArray(size: Int): Array<BoardData?> {
            return arrayOfNulls(size)
        }
    }
}