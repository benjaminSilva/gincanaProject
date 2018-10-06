package com.bsoftwares.benjamin.ideia01.GameModes.GinganaGame

import android.os.Parcel
import android.os.Parcelable
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Time () : Parcelable{

    lateinit var nome : String
    lateinit var perguntas : java.util.ArrayList<Any>
    var pontuacao : Int = 0

    constructor(parcel: Parcel) : this() {
        nome = parcel.readString()
        perguntas = parcel.readArrayList(null)
        pontuacao = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeInt(pontuacao)
        parcel.writeList(perguntas)
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