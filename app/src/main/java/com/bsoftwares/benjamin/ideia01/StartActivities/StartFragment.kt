package com.bsoftwares.benjamin.ideia01.StartActivities


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.bsoftwares.benjamin.ideia01.GameModes.GameActivity
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.rules.view.*
import android.widget.SeekBar.OnSeekBarChangeListener
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import android.R.id.edit
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences
import com.bsoftwares.benjamin.ideia01.MainActivity




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
    val TESTE = "TESTE"
    val PRIMEIRO = "Primeiro"
    val SEGUNDO = "Segundo"
    val TERCEIRO = "Terceiro"
    val QUARTO = "Quarto"
    val QUINTO = "Quinto"
    //var isTempo = false

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        if(arguments!=null){
            listaDePerguntas = arguments?.getParcelableArrayList("Perguntas")
        }
        if (listaK!!.isEmpty())
            separarPerguntas()


        /*val alerta: AlertDialog.Builder
        alerta = AlertDialog.Builder(activity)
        alerta.setTitle("Aviso")
        alerta.setIcon(R.mipmap.ic_launcher)
                .setMessage("ATEN��O: Aplicatico com funcionalidade limitada!!!")
                .setPositiveButton("OK") { dialogInterface, i ->
                    val editor = sharedPref.edit()
                    if (sharedPref.contains(TESTE)){
                        var inteiro = sharedPref.getInt(TESTE,0)
                        if (sharedPref.getInt(TESTE,0)<5){
                            editor.putInt(TESTE,inteiro+1).apply()
                        }else{
                            activity!!.finishAndRemoveTask()
                        }
                    } else{
                        editor.putInt(TESTE,0).apply()
                    }
                }

        val alertDialog = alerta.create()
        alertDialog.show()*/



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
                dialogView.sbNquestoes.progress = sharedPref.getInt("progressoNperguntas",5)
            } else{
                dialogView.btnKids.isChecked = true
                dialogView.btnFacil.isChecked = true
                dialogView.btnMedio.isChecked = true
                dialogView.btnDificil.isChecked = true
                nMaxQuestoes = listaDePerguntas!!.size
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
        }
    }

    fun btnFunction ( view : View, progresso : Int , button : CheckBox) {
        if(!button.isChecked){
            if(checkCounter<=1){
                button.isChecked = true
                Toast.makeText(context,"Pelo menos uma dificuldade deve ficar selecionada",Toast.LENGTH_SHORT).show()
            } else{
                view.sbNquestoes.max = getMax(view)
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
