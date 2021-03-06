package com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Time () : Parcelable{

    lateinit var nome : String
    var pontuacao : Int = 0
    var pontosAjuste : Int = 0

    constructor(parcel: Parcel) : this() {
        nome = parcel.readString()
        pontuacao = parcel.readInt()
        pontosAjuste = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeInt(pontuacao)
        parcel.writeInt(pontosAjuste)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Time> {
        override fun createFromParcel(parcel: Parcel): Time {
            return Time(parcel)
        }

        override fun newArray(size: Int): Array<Time?> {
            return arrayOfNulls(size)
        }
    }

}