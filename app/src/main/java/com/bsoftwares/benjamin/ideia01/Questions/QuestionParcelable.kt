package com.bsoftwares.benjamin.ideia01.Questions

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class QuestionParcelable() : Parcelable {

    lateinit var answerB: String

    lateinit var answerC: String

    lateinit var answerD: String

    lateinit var correctAnswer: String

    lateinit var question: String

    lateinit var dificulty: String

    lateinit var id: String

    var selectedAnswer = ""

    lateinit var reference: String

    constructor(parcel: Parcel) : this() {
        answerB = parcel.readString()
        answerC = parcel.readString()
        answerD = parcel.readString()
        correctAnswer = parcel.readString()
        question = parcel.readString()
        dificulty = parcel.readString()
        id = parcel.readString()
        selectedAnswer = parcel.readString()
        reference = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(answerB)
        parcel.writeString(answerC)
        parcel.writeString(answerD)
        parcel.writeString(correctAnswer)
        parcel.writeString(question)
        parcel.writeString(dificulty)
        parcel.writeString(id)
        parcel.writeString(selectedAnswer)
        parcel.writeString(reference)
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