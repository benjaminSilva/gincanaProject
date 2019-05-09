package com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast

import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_gincana_teams.*

class GincanaTeams : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gincana_teams, container, false)
    }

    val editTexts = ArrayList<EditText>()
    val times = ArrayList<Time>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        editTexts.add(edtTeam1)
        editTexts.add(edtTeam2)

        btnAdicionarTime3.setOnClickListener {
            if (btnAdicionarTime3.text == getString(R.string.mais)) {
                editTexts.add(edtTeam3)
                edtTeam3.visibility = View.VISIBLE
                btnAdicionarTime4.visibility = View.VISIBLE
                btnAdicionarTime3.text = getString(R.string.menos)
            } else {
                editTexts.remove(edtTeam3)
                if (editTexts.contains(edtTeam4))
                    editTexts.remove(edtTeam4)
                edtTeam3.visibility = View.GONE
                edtTeam4.visibility = View.GONE
                btnAdicionarTime4.visibility = View.GONE
                btnAdicionarTime3.text = getString(R.string.mais)
                btnAdicionarTime4.text = getString(R.string.mais)
            }
        }
        btnAdicionarTime4.setOnClickListener {
            if (btnAdicionarTime4.text == getString(R.string.mais)) {
                editTexts.add(edtTeam4)
                edtTeam4.visibility = View.VISIBLE
                btnAdicionarTime4.text = getString(R.string.menos)
            } else {
                editTexts.remove(edtTeam4)
                edtTeam4.visibility = View.GONE
                btnAdicionarTime4.text = getString(R.string.mais)
            }
        }

        edtTeam2.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (edtTeam3.visibility != View.VISIBLE)
                    irParaProximaTela()
                return@OnKeyListener true
            }
            false
        })

        edtTeam3.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (edtTeam4.visibility != View.VISIBLE)
                    irParaProximaTela()
                return@OnKeyListener true
            }
            false
        })

        edtTeam4.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                irParaProximaTela()
                return@OnKeyListener true
            }
            false
        })

        btnNextGincanaTeams.setOnClickListener {
            irParaProximaTela()
        }

    }

    private fun irParaProximaTela(){
        if (areAllNamesOk()){
            val gincanaSettings = GincanaSettings()
            val bundle = Bundle()
            bundle.putParcelableArrayList("Times",times)
            bundle.putParcelableArrayList("PerguntasSelecionadas", arguments!!.getParcelableArrayList("PerguntasSelecionadas"))
            gincanaSettings.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameGame, gincanaSettings, "GameGincanaScores").commit()
        }
    }

    private fun areAllNamesOk(): Boolean {
        for (editText: EditText in editTexts) {
            if (editText.text.toString() != "") {
                var time = Time()
                time.nome = editText.text.toString()
                times.add(time)
            } else {
                times.clear()
                Toast.makeText(context, "Crie o nome dos times", Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true
    }


}
