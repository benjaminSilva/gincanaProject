package com.bsoftwares.benjamin.ideia01.StartActivities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.GameModes.GameActivity
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_show_game.*

class StartShowGameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_game, container, false)
    }

    var listaD : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaF : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaM : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaI : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaTodasPerguntas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaPerguntasSelecionadas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments!=null){
            listaTodasPerguntas = arguments?.getParcelableArrayList("Perguntas")
        }

        if (listaM!!.isEmpty()){
            separarPerguntas()
        }

        btnStartGameShow.setOnClickListener {
            selecionarPerguntas()
            startActivity(Intent(context, GameActivity::class.java).putExtra("PerguntasSelecionadas",listaPerguntasSelecionadas).putExtra("isGameShow",true))
            listaPerguntasSelecionadas!!.clear()
        }
    }

    fun separarPerguntas(){
        for (pergunta : QuestionParcelable in listaTodasPerguntas!!){
            if(pergunta.dificulty=="F")
                listaF!!.add(pergunta)
            if(pergunta.dificulty=="M")
                listaM!!.add(pergunta)
            if(pergunta.dificulty=="D")
                listaD!!.add(pergunta)
            if (pergunta.dificulty=="I")
                listaI!!.add(pergunta)
        }
    }

    fun selecionarPerguntas(){
        listaF!!.shuffle()
        listaM!!.shuffle()
        listaD!!.shuffle()
        listaI!!.shuffle()

        for (i in 0..4){
            listaPerguntasSelecionadas!!.add(listaF!![i])
        }
        for (i in 0..4){
            listaPerguntasSelecionadas!!.add(listaM!![i])
        }
        for (i in 0..4){
            listaPerguntasSelecionadas!!.add(listaD!![i])
        }
        listaPerguntasSelecionadas!!.add(listaI!![0])
    }
}
