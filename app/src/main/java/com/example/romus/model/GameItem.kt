package com.example.romus.model

import android.os.Parcel
import android.os.Parcelable

data class GameItem(val title: String, val imageRes: Int, val thumbRes: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(imageRes)
        parcel.writeInt(thumbRes)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<GameItem> {
        override fun createFromParcel(parcel: Parcel): GameItem = GameItem(parcel)
        override fun newArray(size: Int): Array<GameItem?> = arrayOfNulls(size)
    }
}

