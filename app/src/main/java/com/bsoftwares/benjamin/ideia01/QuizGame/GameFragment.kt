package com.bsoftwares.benjamin.ideia01.QuizGame


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.*
import kotlinx.android.synthetic.main.fragment_game.*
import java.text.FieldPosition
import java.util.*


class GameFragment : Fragment() {


    //Variáveis
    val resultsFragment : Fragment = ResultsFragment()
    var lista : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaSelecionadas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var perguntas : ArrayList<String> = ArrayList()
    var pontuacao : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments!=null){
            lista = arguments?.getParcelableArrayList("Perguntas")
            listaSelecionadas = arguments?.getParcelableArrayList("PerguntasSelecionadas")
            updateView(0)
        }
    }

    private fun updateView(position: Int) {
        if(position>=listaSelecionadas!!.size) {
            val bundle = Bundle()
            bundle.putParcelableArrayList("Perguntas",lista)
            bundle.putParcelable("GameInfo",GameInfo(pontuacao,listaSelecionadas!!.size))
            resultsFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameGame,resultsFragment).commit()
            return
        }

        btnAnswer1.setBackgroundColor(ContextCompat.getColor(context!!,R.color.regularAnswer))
        btnAnswer2.setBackgroundColor(ContextCompat.getColor(context!!,R.color.regularAnswer))
        btnAnswer3.setBackgroundColor(ContextCompat.getColor(context!!,R.color.regularAnswer))
        btnAnswer4.setBackgroundColor(ContextCompat.getColor(context!!,R.color.regularAnswer))

        perguntas.add(listaSelecionadas!![position].correctAnswer)
        perguntas.add(listaSelecionadas!![position].answerB)
        perguntas.add(listaSelecionadas!![position].answerC)
        perguntas.add(listaSelecionadas!![position].answerD)
        perguntas.shuffle()
        TxtQuestion.text = listaSelecionadas!![position].question
        TxtQuestionNumber.text = getString(R.string.pergunta,position+1)
        btnAnswer1.text = perguntas[0]
        btnAnswer2.text = perguntas[1]
        btnAnswer3.text = perguntas[2]
        btnAnswer4.text = perguntas[3]
        perguntas.clear()

        btnAnswer1.setOnClickListener {
            btnAnswer1
            if(btnAnswer1.text == listaSelecionadas!![position].correctAnswer){
                btnAnswer1.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                pontuacao++
            }
            else{
                btnAnswer1.setBackgroundColor(ContextCompat.getColor(context!!,R.color.wrongAnswer))
                if(btnAnswer2.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer2.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer3.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer3.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer4.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer4.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
            }

            Handler().postDelayed({
                updateView(position+1)
                gameLayout.isClickable = true
            }, 1200)

        }
        btnAnswer2.setOnClickListener {
            gameLayout.isClickable = false
            if(btnAnswer2.text == listaSelecionadas!![position].correctAnswer){
                btnAnswer2.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                pontuacao++
            }
            else{
                btnAnswer2.setBackgroundColor(ContextCompat.getColor(context!!,R.color.wrongAnswer))
                if(btnAnswer1.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer1.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer3.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer3.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer4.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer4.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
            }

            Handler().postDelayed({
                updateView(position+1)
                gameLayout.isClickable = true
            }, 1200)


        }
        btnAnswer3.setOnClickListener {
            gameLayout.isClickable = false
            if(btnAnswer3.text == listaSelecionadas!![position].correctAnswer){
                btnAnswer3.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                pontuacao++
            }
            else{
                btnAnswer3.setBackgroundColor(ContextCompat.getColor(context!!,R.color.wrongAnswer))
                if(btnAnswer1.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer1.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer2.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer2.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer4.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer4.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
            }


            Handler().postDelayed({
                updateView(position+1)
                gameLayout.isClickable = true
            }, 1200)


        }
        btnAnswer4.setOnClickListener {
            gameLayout.isClickable = false
            if(btnAnswer4.text == listaSelecionadas!![position].correctAnswer){
                btnAnswer4.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                pontuacao++
            }
            else{
                btnAnswer4.setBackgroundColor(ContextCompat.getColor(context!!,R.color.wrongAnswer))
                if(btnAnswer1.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer1.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer3.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer3.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
                if(btnAnswer2.text == listaSelecionadas!![position].correctAnswer)
                    btnAnswer2.setBackgroundColor(ContextCompat.getColor(context!!,R.color.correctAnswer))
            }

            Handler().postDelayed({
                updateView(position+1)
                gameLayout.isClickable = true
            }, 1200)
        }
    }

}
