package com.bsoftwares.benjamin.ideia01.questions

import android.os.Parcel
import android.os.Parcelable
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class QuestionParcelable(var answerB: String, var answerC: String,var answerD: String,var correctAnswer: String,var question: String, var dificulty: String,var id: String, var timesAcertaram: ArrayList<String>, var reference: String, var selectedAnswer: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(answerB)
        writeString(answerC)
        writeString(answerD)
        writeString(correctAnswer)
        writeString(question)
        writeString(dificulty)
        writeString(id)
        writeStringList(timesAcertaram)
        writeString(reference)
        writeString(selectedAnswer)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<QuestionParcelable> = object : Parcelable.Creator<QuestionParcelable> {
            override fun createFromParcel(source: Parcel): QuestionParcelable = QuestionParcelable(source)
            override fun newArray(size: Int): Array<QuestionParcelable?> = arrayOfNulls(size)
        }
    }
}