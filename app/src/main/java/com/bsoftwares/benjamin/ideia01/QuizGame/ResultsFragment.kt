package com.bsoftwares.benjamin.ideia01.QuizGame


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.MainActivity
import com.bsoftwares.benjamin.ideia01.QuestionParcelable

import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_results.*

class ResultsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    var lista : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var gameInfo : GameInfo = GameInfo()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameInfo = arguments!!.getParcelable("GameInfo")
        lista = arguments!!.getParcelableArrayList("Perguntas")
        txtResultado.text = getString(R.string.pontuacao,gameInfo.nCertas,gameInfo.nTotal)
        btnRetorna.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java).putExtra("Perguntas",lista))
        }
    }


}
