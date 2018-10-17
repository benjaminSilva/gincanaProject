package com.bsoftwares.benjamin.ideia01.questions

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class QuestionsRealmDB : RealmObject(){

    @PrimaryKey
    lateinit var primaryKey : String

    var perguntas : RealmList<QuestionRealmObject> = RealmList()
}