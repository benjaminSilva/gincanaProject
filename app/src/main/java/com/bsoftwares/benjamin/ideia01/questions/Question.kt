package com.bsoftwares.benjamin.ideia01.questions


import com.google.firebase.database.DataSnapshot

class Question (snapshot: DataSnapshot){

    lateinit var answerB: String

    lateinit var answerC: String

    lateinit var answerD: String

    lateinit var correctAnswer: String

    lateinit var question: String

    lateinit var dificulty: String

    lateinit var id: String

    lateinit var reference: String

    init {
        try {
            val data : HashMap<String,Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key?: ""
            question = data["question"] as String
            answerB = data["answerB"] as String
            answerC = data["answerC"] as String
            answerD = data["answerD"] as String
            correctAnswer = data["correctAnswer"] as String
            dificulty = data["dificulty"] as String
            reference = data["reference"] as String
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}