package com.bsoftwares.benjamin.ideia01

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
//import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.Questions.Question
import com.bsoftwares.benjamin.ideia01.Questions.QuestionModel
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.StartActivities.StartFragment
import com.bsoftwares.benjamin.ideia01.StartActivities.StartGincanaFragment
import com.bsoftwares.benjamin.ideia01.StartActivities.StartShowGameFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), Observer{


    val startFragment = StartFragment()
    val showFragment = StartShowGameFragment()
    val gincanaFragment = StartGincanaFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        QuestionModel.addObserver(this)
        if(intent.extras!=null){
            carregandoMain.visibility = View.INVISIBLE
            var bundle = Bundle()
            bundle.putParcelableArrayList("Perguntas", intent.extras!!.getParcelableArrayList<QuestionParcelable>("Perguntas"))
            startFragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameMain,startFragment).commit()
        }
    }

    override fun update(p0: Observable?, p1: Any?) {
        val dataFirabase = QuestionModel.getData()
        if(dataFirabase!=null){
            data = dataFirabase
            criarParcelable()
            var bundle = Bundle()
            bundle.putParcelableArrayList("Perguntas",lista)
            startFragment.arguments = bundle
            carregandoMain.visibility = View.INVISIBLE
            navigation.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frameMain,startFragment).commit()
        }
    }

    var data : java.util.ArrayList<Question>? = java.util.ArrayList()
    val lista : java.util.ArrayList<QuestionParcelable> = java.util.ArrayList()

    private fun criarParcelable() {
        for ( question : Question in data!!){
            val questionParcelable = QuestionParcelable(question.answerB,question.answerC,question.answerD, question.correctAnswer,question.question, question.dificulty,question.id, ArrayList(), question.reference, "")
            lista.add(questionParcelable)
        }

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList("Perguntas",lista)
                startFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.frameMain,startFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList("Perguntas",lista)
                showFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.frameMain,showFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList("Perguntas",lista)
                gincanaFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.frameMain,gincanaFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



}
