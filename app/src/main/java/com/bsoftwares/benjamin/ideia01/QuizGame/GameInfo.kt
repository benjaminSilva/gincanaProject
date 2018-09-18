package com.bsoftwares.benjamin.ideia01.QuizGame

import android.os.Parcel
import android.os.Parcelable

class GameInfo() : Parcelable {
    var nCertas: Int = 0
    var nTotal: Int = 0

    constructor(certas : Int,total:Int) : this(){
        nCertas = certas
        nTotal = total
    }

    constructor(parcel: Parcel) : this() {
        nCertas = parcel.readInt()
        nTotal = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(nCertas)
        parcel.writeInt(nTotal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameInfo> {
        override fun createFromParcel(parcel: Parcel): GameInfo {
            return GameInfo(parcel)
        }

        override fun newArray(size: Int): Array<GameInfo?> {
            return arrayOfNulls(size)
        }
    }
}