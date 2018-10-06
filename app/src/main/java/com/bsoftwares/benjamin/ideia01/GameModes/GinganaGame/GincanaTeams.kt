package com.bsoftwares.benjamin.ideia01.GameModes.GinganaGame


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable

import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_gincana_teams.*


class GincanaTeams : Fragment(), SeekBar.OnSeekBarChangeListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gincana_teams, container, false)
    }

    val editTexts = ArrayList<EditText>()
    val times = ArrayList<Time>()
    var gincanaScores = GincanaScores()
    var listaSelecionadas: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTexts.add(edtTeam1)
        editTexts.add(edtTeam2)

        rbApenasUmTime.isChecked = true

        sbKids.setOnSeekBarChangeListener(this)
        sbFacil.setOnSeekBarChangeListener(this)
        sbMedio.setOnSeekBarChangeListener(this)
        sbDificil.setOnSeekBarChangeListener(this)
        sbKids.max = 20
        sbFacil.max = 20
        sbMedio.max = 20
        sbDificil.max = 20
        sbKids.progress = 5
        sbFacil.progress = 10
        sbMedio.progress = 15
        sbDificil.progress = 20
        tvKidsPoints.text = 25.toString()
        tvFacilPoints.text = 50.toString()
        tvMedioPoints.text = 75.toString()
        tvDificilPoints.text = 100.toString()

        btnNextGincanaRules.setOnClickListener {
            if(areAllNamesOk()){
                val bundle = Bundle()
                bundle.putParcelableArray("Perguntas",arguments!!.getParcelableArray("Perguntas"))
            }
        }

        btnAdicionarTime3.setOnClickListener {
            if(btnAdicionarTime3.text == "+"){
                editTexts.add(edtTeam3)
                edtTeam3.visibility = View.VISIBLE
                btnAdicionarTime4.visibility = View.VISIBLE
                btnAdicionarTime3.text = "-"
            }else{
                editTexts.remove(edtTeam3)
                if (editTexts.contains(edtTeam4))
                    editTexts.remove(edtTeam4)
                edtTeam3.visibility = View.GONE
                edtTeam4.visibility = View.GONE
                btnAdicionarTime4.visibility = View.GONE
                btnAdicionarTime3.text = "+"
                btnAdicionarTime4.text = "+"
            }
        }
        btnAdicionarTime4.setOnClickListener {
            if (btnAdicionarTime4.text == "+"){
                editTexts.add(edtTeam4)
                edtTeam4.visibility = View.VISIBLE
                btnAdicionarTime4.text = "-"
            }else{
                editTexts.remove(edtTeam4)
                edtTeam4.visibility = View.GONE
                btnAdicionarTime4.text = "+"
            }
        }

        btnNextGincanaRules.setOnClickListener {
            val pontos = ArrayList<Int>()
            var todosRespondem = true
            if (rbApenasUmTime.isChecked)
                todosRespondem = false
            pontos.add(sbKids.progress*5)
            pontos.add(sbFacil.progress*5)
            pontos.add(sbMedio.progress*5)
            pontos.add(sbDificil.progress*5)
            var bundle = Bundle()
            bundle.putIntegerArrayList("Pontos",pontos)
            bundle.putBoolean("umOuTodos",todosRespondem)
            bundle.putParcelableArrayList("Times",times)
            bundle.putParcelableArrayList("PerguntasSelecionadas",arguments!!.getParcelableArrayList("PerguntasSelecionadas"))
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameGame, gincanaScores,"GameGincanaScores").commit()
        }

    }

    private fun areAllNamesOk() : Boolean {
        for (editText : EditText in editTexts){
            if (editText.text.toString() != ""){
                var time = Time()
                time.nome = editText.text.toString()
                time.perguntas = java.util.ArrayList()
                times.add(time)
            }
            else{
                times.clear()
                Toast.makeText(context,"Crie o nome dos times",Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
        var nVezesCinco = progress * 5
        if (progress<1){
            seekBar!!.progress = 1
            return
        }

        when (seekBar){
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