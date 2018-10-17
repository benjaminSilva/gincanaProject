package com.bsoftwares.benjamin.ideia01.questions

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class QuestionRealmObject(answerB: String, answerC: String,answerD: String, correctAnswer: String, question: String, dificulty: String, id: String, reference: String) : RealmObject(){

    var answerB: String = answerB
    var answerC: String = answerC
    var answerD: String  = answerD
    var correctAnswer: String = correctAnswer
    var question: String = question
    var dificulty: String = dificulty
    var id: String = id
    var reference: String = reference

}