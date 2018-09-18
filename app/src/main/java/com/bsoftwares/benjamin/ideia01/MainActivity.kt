package com.bsoftwares.benjamin.ideia01

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
//import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), Observer{

    override fun update(p0: Observable?, p1: Any?) {
        val dataFirabase = QuestionModel.getData()
        if(dataFirabase!=null){
            data = dataFirabase
            criarParcelable()
            bundle.putParcelableArrayList("Perguntas",lista)
            startFragment.arguments = bundle
            carregandoMain.visibility = View.INVISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frameMain,startFragment).commit()
        }
    }

    var data : java.util.ArrayList<Question>? = java.util.ArrayList()
    val lista : java.util.ArrayList<QuestionParcelable> = java.util.ArrayList()

    private fun criarParcelable() {
        for ( question : Question in data!!){
            val questionParcelable = QuestionParcelable()
            questionParcelable.answerB = question.answerB
            questionParcelable.answerC = question.answerC
            questionParcelable.answerD = question.answerD
            questionParcelable.question = question.question
            questionParcelable.id = question.id
            questionParcelable.dificulty = question.dificulty
            questionParcelable.correctAnswer = question.correctAnswer
            lista.add(questionParcelable)
        }

    }


    /*private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }*/

    val startFragment : Fragment = StartFragment()
    val bundle : Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        QuestionModel.addObserver(this)
        if(intent.extras!=null){
            carregandoMain.visibility = View.INVISIBLE
            bundle.putParcelableArrayList("Perguntas", intent.extras.getParcelableArrayList<QuestionParcelable>("Perguntas"))
            startFragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameMain,startFragment).commit()
        }

        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
