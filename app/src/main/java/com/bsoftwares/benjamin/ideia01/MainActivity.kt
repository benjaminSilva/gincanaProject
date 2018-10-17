package com.bsoftwares.benjamin.ideia01

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
//import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bsoftwares.benjamin.ideia01.startactivities.StartFragment
import com.bsoftwares.benjamin.ideia01.startactivities.StartGincanaFragment
import com.bsoftwares.benjamin.ideia01.startactivities.StartShowGameFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import java.nio.file.Files.size
import android.R.attr.key
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.questions.*


class MainActivity : AppCompatActivity(), Observer {


    val startFragment = StartFragment()
    val showFragment = StartShowGameFragment()
    val gincanaFragment = StartGincanaFragment()
    val app = App()
    lateinit var realm : Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        QuestionModel.addObserver(this)
        realm = Realm.getDefaultInstance()
        loadFromDB()
        if (intent.extras != null) {
            carregandoMain.visibility = View.INVISIBLE
            var bundle = Bundle()
            bundle.putParcelableArrayList(
                "Perguntas",
                intent.extras!!.getParcelableArrayList<QuestionParcelable>("Perguntas")
            )
            startFragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameMain, startFragment).commit()
        }
    }

    override fun update(p0: Observable?, p1: Any?) {
        val dataFirabase = QuestionModel.getData()
        if (dataFirabase != null) {
            data = dataFirabase
            criarParcelable()
            carregandoMain.visibility = View.INVISIBLE
            navigation.visibility = View.VISIBLE
            navigation.selectedItemId = R.id.game_show
        }
    }


    var data: java.util.ArrayList<Question>? = java.util.ArrayList()
    val lista: java.util.ArrayList<QuestionParcelable> = java.util.ArrayList()

    private fun criarParcelable() {
        realm.beginTransaction()
        val perguntasRealm = realm.createObject(QuestionsRealmDB::class.java)
        for (question: Question in data!!) {
            val questionParcelable = QuestionParcelable(question.answerB, question.answerC, question.answerD, question.correctAnswer, question.question, question.dificulty, question.id, ArrayList(), question.reference, "")
            val questionRealmObject = QuestionRealmObject(question.answerB, question.answerC, question.answerD, question.correctAnswer, question.question, question.dificulty, question.id, question.reference)
            realm.copyToRealm(questionRealmObject)
            perguntasRealm.perguntas.add(questionRealmObject)
            lista.add(questionParcelable)
        }
        writeToDB(perguntasRealm)
        realm.commitTransaction()
    }

    fun writeToDB(perguntas: QuestionsRealmDB) {
        realm.executeTransactionAsync({ brealm -> brealm.insertOrUpdate(perguntas) }, {
            // TODO: REMOVER O LOADING AQUI.
        }, {
            // TODO: REMOVER O LOADING AQUI.
        }
        )
    }

    fun loadFromDB(){
        realm.executeTransaction { brealm ->
            val realmDB = brealm.where(QuestionRealmObject::class.java).equalTo("primaryKey", "Perguntas").findAll()
            if (realmDB != null) {
                Toast.makeText(this,realmDB[0].toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.free_play -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList("Perguntas", lista)
                startFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.frameMain, startFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.game_show -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList("Perguntas", lista)
                showFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.frameMain, showFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.gincana -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList("Perguntas", lista)
                gincanaFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.frameMain, gincanaFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
