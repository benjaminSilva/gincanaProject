package com.bsoftwares.benjamin.ideia01


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.QuizGame.GameActivity
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.rules.view.*

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!


        if(arguments!=null){
            listaDePerguntas = arguments?.getParcelableArrayList("Perguntas")
        }
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
                startActivity(Intent(context, GameActivity::class.java).putExtra("Perguntas", listaDePerguntas).putExtra("PerguntasSelecionadas",listaPerguntasSelecionadas))
            }else{
                listaPerguntasSelecionadas = listaDePerguntas
                startActivity(Intent(context, GameActivity::class.java).putExtra("Perguntas", listaDePerguntas).putExtra("PerguntasSelecionadas",listaPerguntasSelecionadas))
            }
        }

        btnRules.setOnClickListener { it ->
            val dialogView = activity!!.layoutInflater.inflate(R.layout.rules,null)

            var checkCounter = 0

            if(sharedPref.contains("perguntasCrianca")){
                if(sharedPref.getBoolean("perguntasCrianca",false)){
                    dialogView.btnKids.isChecked = true
                    checkCounter++
                }
                if(sharedPref.getBoolean("perguntasFaceis",false)){
                    dialogView.btnFacil.isChecked = true
                    checkCounter++
                }
                if(sharedPref.getBoolean("perguntasMedianas",false)){
                    dialogView.btnMedio.isChecked = true
                    checkCounter++
                }
                if(sharedPref.getBoolean("perguntasDificeis",false)){
                    dialogView.btnDificil.isChecked = true
                    checkCounter++
                }
            } else{
                dialogView.btnKids.isChecked = true
                dialogView.btnFacil.isChecked = true
                dialogView.btnMedio.isChecked = true
                dialogView.btnDificil.isChecked = true
                checkCounter = 4
            }

            val builder = AlertDialog.Builder(context)
            dialogView.btnKids.setOnClickListener {
                if(!dialogView.btnKids.isChecked){
                    if(checkCounter<=1){
                        dialogView.btnKids.isChecked = true
                        Toast.makeText(context,"Pelo menos uma dificuldade deve ficar selecionada",Toast.LENGTH_SHORT).show()
                    } else{
                        checkCounter--
                    }
                }else{
                    checkCounter++
                }
            }
            dialogView.btnFacil.setOnClickListener {
                if(!dialogView.btnFacil.isChecked){
                    if(checkCounter<=1){
                        Toast.makeText(context,"Pelo menos uma dificuldade deve ficar selecionada",Toast.LENGTH_SHORT).show()
                        dialogView.btnFacil.isChecked = true
                    } else{
                        checkCounter--
                    }
                }else{
                    checkCounter++
                }

            }
            dialogView.btnMedio.setOnClickListener {
                if(!dialogView.btnMedio.isChecked){
                    if(checkCounter<=1){
                        dialogView.btnMedio.isChecked = true
                        Toast.makeText(context,"Pelo menos uma dificuldade deve ficar selecionada",Toast.LENGTH_SHORT).show()
                    } else{
                        checkCounter--
                    }
                }else{
                    checkCounter++
                }

            }
            dialogView.btnDificil.setOnClickListener {
                if(!dialogView.btnDificil.isChecked){
                    if(checkCounter<=1){
                        dialogView.btnDificil.isChecked = true
                        Toast.makeText(context,"Pelo menos uma dificuldade deve ficar selecionada",Toast.LENGTH_SHORT).show()
                    } else{
                        checkCounter--
                    }
                }else{
                    checkCounter++
                }

            }
            builder.setOnDismissListener {
                with (sharedPref.edit()) {
                    putBoolean("perguntasCrianca",dialogView.btnKids.isChecked)
                    putBoolean("perguntasFaceis",dialogView.btnFacil.isChecked)
                    putBoolean("perguntasMedianas",dialogView.btnMedio.isChecked)
                    putBoolean("perguntasDificeis",dialogView.btnDificil.isChecked)
                    apply()
                }
            }
            builder.setView(dialogView)
            val regras = builder.create()
            regras.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            regras.show()
        }
    }
}
