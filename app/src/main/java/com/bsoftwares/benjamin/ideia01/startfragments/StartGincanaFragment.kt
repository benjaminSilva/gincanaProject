package com.bsoftwares.benjamin.ideia01.startfragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.gamemodes.GameActivity
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable

import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_start_gincana.*


class StartGincanaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start_gincana, container, false)
    }

    var listaTodasPerguntas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments!=null){
            listaTodasPerguntas = arguments?.getParcelableArrayList("Perguntas")
        }

        btnGincanaStart.setOnClickListener {
            startActivity(Intent(context, GameActivity::class.java).putExtra("PerguntasSelecionadas",listaTodasPerguntas).putExtra("isGincana",true))
        }
    }
}
