package com.bsoftwares.benjamin.ideia01

import android.os.Parcel
import android.os.Parcelable

class QuestionParcelable() : Parcelable {

    lateinit var answerB: String

    lateinit var answerC: String

    lateinit var answerD: String

    lateinit var correctAnswer: String

    lateinit var question: String

    lateinit var dificulty: String

    lateinit var id: String

    constructor(parcel: Parcel) : this() {
        answerB = parcel.readString()
        answerC = parcel.readString()
        answerD = parcel.readString()
        correctAnswer = parcel.readString()
        question = parcel.readString()
        dificulty = parcel.readString()
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(answerB)
        parcel.writeString(answerC)
        parcel.writeString(answerD)
        parcel.writeString(correctAnswer)
        parcel.writeString(question)
        parcel.writeString(dificulty)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionParcelable> {
        override fun createFromParcel(parcel: Parcel): QuestionParcelable {
            return QuestionParcelable(parcel)
        }

        override fun newArray(size: Int): Array<QuestionParcelable?> {
            return arrayOfNulls(size)
        }
    }
}