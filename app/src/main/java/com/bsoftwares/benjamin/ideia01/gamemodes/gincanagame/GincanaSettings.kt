package com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast

import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_gincana_settings.*


class GincanaSettings : Fragment(), SeekBar.OnSeekBarChangeListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gincana_settings, container, false)
    }

    var gincanaScores = GincanaScores()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rbTodosTimes.isChecked = true
        sbKids.setOnSeekBarChangeListener(this)
        sbFacil.setOnSeekBarChangeListener(this)
        sbMedio.setOnSeekBarChangeListener(this)
        sbDificil.setOnSeekBarChangeListener(this)
        sbKids.max = 10
        sbFacil.max = 10
        sbMedio.max = 10
        sbDificil.max = 10
        sbKids.progress = 3
        sbFacil.progress = 4
        sbMedio.progress = 7
        sbDificil.progress = 10
        tvKidsPoints.text = 30.toString()
        tvFacilPoints.text = 40.toString()
        tvMedioPoints.text = 70.toString()
        tvDificilPoints.text = 100.toString()

        btnNextGincanaRules.setOnClickListener {
            val pontos = ArrayList<Int>()
            var todosRespondem = true
            if (rbApenasUmTime.isChecked)
                todosRespondem = false
            pontos.add(sbKids.progress * 10)
            pontos.add(sbFacil.progress * 10)
            pontos.add(sbMedio.progress * 10)
            pontos.add(sbDificil.progress * 10)
            val bundle = Bundle()
            bundle.putIntegerArrayList("Pontos", pontos)
            bundle.putBoolean("umOuTodos", todosRespondem)
            bundle.putParcelableArrayList("Times", arguments!!.getParcelableArrayList("Times"))
            bundle.putParcelableArrayList("PerguntasSelecionadas", arguments!!.getParcelableArrayList("PerguntasSelecionadas"))
            gincanaScores.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameGame, gincanaScores, "GameGincanaScores").commit()
        }
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
        val nVezesCinco = progress * 10
        if (progress < 1) {
            seekBar!!.progress = 1
            return
        }

        when (seekBar) {
            sbKids -> {
                tvKidsPoints.text = nVezesCinco.toString()
            }
            sbFacil -> {
                tvFacilPoints.text = nVezesCinco.toString()
            }
            sbMedio -> {
                tvMedioPoints.text = nVezesCinco.toString()
            }
            sbDificil -> {
                tvDificilPoints.text = nVezesCinco.toString()
            }

        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}
