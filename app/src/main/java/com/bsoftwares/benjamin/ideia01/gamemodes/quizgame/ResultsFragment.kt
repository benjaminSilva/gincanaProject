package com.bsoftwares.benjamin.ideia01.gamemodes.quizgame


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable

import com.bsoftwares.benjamin.ideia01.R
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.fragment_results.*
import java.text.DecimalFormat

class ResultsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    var listaSelecionadas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaRespondidas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRetorna.isClickable = false

        YoYo.with(Techniques.SlideInLeft)
                .duration(1000)
                .playOn(clResults)
        Handler().postDelayed({
            btnRetorna.isClickable = true
        }, 1000)
        val decimalFormat = DecimalFormat("###.#")
        listaSelecionadas = arguments!!.getParcelableArrayList("PerguntasSelecionadas")
        val nCorretas = getCorretas(listaSelecionadas!!)
        if(arguments!!.containsKey("pontuacao"))
            txtResultado.text = getString(R.string.resultado_final_game_show,arguments!!.getInt("pontuacao"))
        else
            txtResultado.text = getString(R.string.pontuacao,nCorretas,listaSelecionadas!!.size,decimalFormat.format(nCorretas.toFloat()/listaSelecionadas!!.size.toFloat()*100))
        rvResultados.layoutManager = LinearLayoutManager(context)
        rvResultados.adapter = ResultsAdapter(listaRespondidas!!,context!!)
        btnRetorna.setOnClickListener {
            activity!!.finish()
        }
    }

    fun getCorretas(listaSel : ArrayList<QuestionParcelable>) : Int{
        var corretas = 0
        for (pergunta : QuestionParcelable in listaSel){
            if (pergunta.selectedAnswer != "")
                listaRespondidas!!.add(pergunta)
            if (pergunta.selectedAnswer == pergunta.correctAnswer)
                corretas++
        }
        return corretas
    }


}
