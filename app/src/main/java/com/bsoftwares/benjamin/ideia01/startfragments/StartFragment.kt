package com.bsoftwares.benjamin.ideia01.startfragments


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.gamemodes.GameActivity
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.rules.view.*
import android.widget.SeekBar.OnSeekBarChangeListener
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import android.annotation.SuppressLint


class StartFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    var listaDePerguntas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaK : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaD : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaF : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaM : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var checkCounter = 0


    fun separarPerguntas(){
        for (pergunta : QuestionParcelable in listaDePerguntas!!){
            if(pergunta.dificulty=="K")
                listaK!!.add(pergunta)
            if(pergunta.dificulty=="F")
                listaF!!.add(pergunta)
            if(pergunta.dificulty=="M")
                listaM!!.add(pergunta)
            if(pergunta.dificulty=="D")
                listaD!!.add(pergunta)

        }
    }

    fun getMax(view : View): Int {
        var total = 0
        if (view.btnKids.isChecked)
            total+=listaK!!.size
        if (view.btnFacil.isChecked)
            total+=listaF!!.size
        if (view.btnMedio.isChecked)
            total+=listaM!!.size
        if (view.btnDificil.isChecked)
            total+=listaD!!.size

        return total
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        if(arguments!=null){
            listaDePerguntas = arguments?.getParcelableArrayList("Perguntas")
        }
        if (listaK!!.isEmpty()&&!listaDePerguntas!!.isEmpty())
            separarPerguntas()

        BtnStart.setOnClickListener {
            var listaPerguntasSelecionadas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
            if(sharedPref.contains("perguntasCrianca")){
                for (question : QuestionParcelable in listaDePerguntas!!){
                    if(question.dificulty=="K"){
                        if(sharedPref.getBoolean("perguntasCrianca",false)){
                            listaPerguntasSelecionadas!!.add(question)
                        }
                    }
                    if(question.dificulty=="F"){
                        if(sharedPref.getBoolean("perguntasFaceis",false)){
                            listaPerguntasSelecionadas!!.add(question)
                        }
                    }
                    if(question.dificulty=="M"){
                        if(sharedPref.getBoolean("perguntasMedianas",false)){
                            listaPerguntasSelecionadas!!.add(question)
                        }
                    }
                    if(question.dificulty=="D"){
                        if(sharedPref.getBoolean("perguntasDificeis",false)){
                            listaPerguntasSelecionadas!!.add(question)
                        }
                    }
                }
                listaPerguntasSelecionadas!!.shuffle()
                startActivity(Intent(context, GameActivity::class.java).putExtra("PerguntasSelecionadas",ArrayList(listaPerguntasSelecionadas.subList(0,sharedPref.getInt("progressoNperguntas",5)))).putExtra("tempoParaPergunta",sharedPref.getBoolean("tempoParaPergunta",false)))
            }else{
                listaPerguntasSelecionadas = listaDePerguntas
                listaPerguntasSelecionadas!!.shuffle()
                startActivity(Intent(context, GameActivity::class.java).putExtra("PerguntasSelecionadas",listaPerguntasSelecionadas).putExtra("tempoParaPergunta",sharedPref.getBoolean("tempoParaPergunta",false)))
            }
        }

        btnRules.setOnClickListener { it ->
            val dialogView = activity!!.layoutInflater.inflate(R.layout.rules,null)
            var nMaxQuestoes = 0
            var progresso = 5
            checkCounter = 0

            if(sharedPref.contains("perguntasCrianca")){
                if(sharedPref.getBoolean("perguntasCrianca",false)){
                    nMaxQuestoes+=listaK!!.size
                    dialogView.btnKids.isChecked = true
                    checkCounter++
                }
                if(sharedPref.getBoolean("perguntasFaceis",false)){
                    nMaxQuestoes+=listaF!!.size
                    dialogView.btnFacil.isChecked = true
                    checkCounter++
                }
                if(sharedPref.getBoolean("perguntasMedianas",false)){
                    nMaxQuestoes+=listaM!!.size
                    dialogView.btnMedio.isChecked = true
                    checkCounter++
                }
                if(sharedPref.getBoolean("perguntasDificeis",false)){
                    nMaxQuestoes+=listaD!!.size
                    dialogView.btnDificil.isChecked = true
                    checkCounter++
                }
                dialogView.cbTempo.isChecked = sharedPref.getBoolean("tempoParaPergunta",false)
                progresso = sharedPref.getInt("progressoNperguntas",5)
            } else{
                dialogView.btnKids.isChecked = true
                dialogView.btnFacil.isChecked = true
                dialogView.btnMedio.isChecked = true
                dialogView.btnDificil.isChecked = true
                nMaxQuestoes = getMax(dialogView)
                checkCounter = 4
            }
            dialogView.txtProgress.text = getString(R.string.nquestoes,sharedPref.getInt("progressoNperguntas",5),getMax(dialogView))
            val yourSeekBarListener = object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {

                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {

                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    when (seekBar.id) {
                        R.id.sbNquestoes -> if (progress<5) {
                            seekBar.progress = 5
                        }else {
                            dialogView.txtProgress.text = getString(R.string.nquestoes,progress,dialogView.sbNquestoes.max)
                            progresso = progress
                        }
                    }

                }
            }

            dialogView.sbNquestoes.max = nMaxQuestoes
            dialogView.sbNquestoes.progress = progresso
            dialogView.sbNquestoes.setOnSeekBarChangeListener(yourSeekBarListener)

            val builder = AlertDialog.Builder(context)
            dialogView.btnKids.setOnClickListener {
                btnFunction(dialogView,progresso,dialogView.btnKids)
            }
            dialogView.btnFacil.setOnClickListener {
                btnFunction(dialogView,progresso,dialogView.btnFacil)
            }
            dialogView.btnMedio.setOnClickListener {
                btnFunction(dialogView,progresso,dialogView.btnMedio)
            }
            dialogView.btnDificil.setOnClickListener {
                btnFunction(dialogView,progresso,dialogView.btnDificil)
            }
            builder.setOnDismissListener {
                with (sharedPref.edit()) {
                    putBoolean("perguntasCrianca",dialogView.btnKids.isChecked)
                    putBoolean("perguntasFaceis",dialogView.btnFacil.isChecked)
                    putBoolean("perguntasMedianas",dialogView.btnMedio.isChecked)
                    putBoolean("perguntasDificeis",dialogView.btnDificil.isChecked)
                    putBoolean("tempoParaPergunta",dialogView.cbTempo.isChecked)
                    putInt("progressoNperguntas",progresso)
                    apply()
                }
            }
            builder.setView(dialogView)
            val regras = builder.create()

            regras.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            regras.show()

            dialogView.btnOkRules.setOnClickListener {
                regras.dismiss()
            }
        }
    }

    fun btnFunction ( view : View, progresso : Int , button : CheckBox) {
        if(!button.isChecked){
            if(checkCounter<=1){
                button.isChecked = true
                Toast.makeText(context,"Pelo menos uma dificuldade deve ficar selecionada",Toast.LENGTH_SHORT).show()
            } else{
                view.sbNquestoes.max = getMax(view)
                if (progresso>view.sbNquestoes.max)
                    view.txtProgress.text = getString(R.string.nquestoes,view.sbNquestoes.max,view.sbNquestoes.max)
                else
                    view.txtProgress.text = getString(R.string.nquestoes,progresso,view.sbNquestoes.max)
                checkCounter--
            }
        }else{
            view.sbNquestoes.max = getMax(view)
            view.txtProgress.text = getString(R.string.nquestoes,progresso,view.sbNquestoes.max)
            checkCounter++
        }
    }
}
