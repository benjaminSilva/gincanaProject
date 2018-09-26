package com.bsoftwares.benjamin.ideia01.GameModes.QuizGame


import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.*
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.cartas.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.proxima_pergunta.*
import java.util.*
import kotlin.collections.ArrayList


class GameFragment : Fragment() {


    //Variáveis
    val resultsFragment: Fragment = ResultsFragment()
    var listaSelecionadas: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var perguntas: ArrayList<String> = ArrayList()
    var pontuacao = 0
    lateinit var timer: CountDownTimer
    var isTempo = false
    var isGameShow = false
    var tentativaExtra = false
    val listaValores: IntArray = intArrayOf(1000, 2000, 3000,4000,5000,10000,20000,30000,40000,50000,100000,200000,300000,400000,500000,1000000)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            listaSelecionadas = arguments?.getParcelableArrayList("PerguntasSelecionadas")
            if (arguments!!.containsKey("isGameShow")) {
                isGameShow = true
            } else {
                btnTentativaExtra.visibility = View.GONE
                btnAjudaCartas.visibility = View.GONE
                btnReferencia.visibility = View.GONE
                btnDesistir.visibility = View.GONE
            }
            isTempo = arguments!!.getBoolean("tempoParaPergunta")
            if (isTempo && !isGameShow) {
                timer = object : CountDownTimer(listaSelecionadas!!.size.toLong() * 10000, 1000) {
                    override fun onFinish() {
                        irParaResultados()
                        return
                    }
                    override fun onTick(p0: Long) {
                        txtTimerQuiz.text = (p0 / 1000).toString()
                    }
                }.start()
            }
            updateView(0)
        }
    }

    fun desbilitarBotoes() {
        btnAnswer1.isClickable = false
        btnAnswer2.isClickable = false
        btnAnswer3.isClickable = false
        btnAnswer4.isClickable = false
    }

    fun habilitarBotoes() {
        btnAnswer1.isClickable = true
        btnAnswer2.isClickable = true
        btnAnswer3.isClickable = true
        btnAnswer4.isClickable = true
    }

    fun animateAllViews(escolhido: Button, outro1: Button, outro2: Button, outro3: Button, pergunta: TextView, animation: Techniques, duration: Long) {
        YoYo.with(animation)
                .duration(duration)
                .playOn(pergunta)

        YoYo.with(animation)
                .duration(duration)
                .playOn(escolhido)

        YoYo.with(animation)
                .duration(duration)
                .playOn(outro1)

        YoYo.with(animation)
                .duration(duration)
                .playOn(outro2)

        YoYo.with(animation)
                .duration(duration)
                .playOn(outro3)
    }

    fun irParaResultados() {
        val bundle = Bundle()
        bundle.putParcelableArrayList("PerguntasSelecionadas", listaSelecionadas)
        resultsFragment.arguments = bundle
        if(isTempo)
            timer.cancel()
        if(isGameShow)
            bundle.putInt("pontuacao", pontuacao)
        activity!!.supportFragmentManager.beginTransaction().add(R.id.frameGame, resultsFragment).commit()
    }

    fun definirCertoErrado(escolhido: Button, outro1: Button, outro2: Button, outro3: Button, resposta: String, pos: Int) {
        desbilitarBotoes()
        listaSelecionadas!![pos].selectedAnswer = escolhido.text.toString()
        var isCerto = false
        if (escolhido.text == resposta) {
            isCerto = true
            pontuacao = listaValores[pos]
            if (tentativaExtra)
                tentativaExtra = false
            escolhido.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
        } else {
            escolhido.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.wrongAnswer))
            if(!tentativaExtra)
                if (!isGameShow)
                    when (resposta){
                        outro1.text ->outro1.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
                        outro2.text ->outro2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
                        outro3.text ->outro3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
                    }
            if(tentativaExtra){
                updateView(pos)
                return
            }
            when (pos){
                0 -> pontuacao = 0
                15 -> {
                    pontuacao = 0
                } else -> pontuacao = listaValores[pos]/4
            }
        }

        if ((pos + 1) < listaSelecionadas!!.size && !isGameShow)
            animateAllViews(escolhido,outro1,outro2,outro3,TxtQuestion,Techniques.FadeOut,1000)

        if((pos + 1) < listaSelecionadas!!.size && isGameShow)
            YoYo.with(Techniques.FadeOut)
                    .duration(1000)
                    .playOn(gameLayout)

        if(isGameShow){
            timer.cancel()
        }

        Handler().postDelayed({
            if (isGameShow&&!isCerto){
                irParaResultados()
            }else{
                updateView(pos + 1)
                if ((pos + 1) < listaSelecionadas!!.size) {
                    animateAllViews(escolhido,outro1,outro2,outro3,TxtQuestion,Techniques.FadeIn,500)
                    habilitarBotoes()
                }
            }
        }, 700)

    }
    
    fun tratarResultadoCarta(position : Int, valorCarta : Int){
            val buttoes = Arrays.asList(btnAnswer1,btnAnswer2,btnAnswer3,btnAnswer4)
            buttoes.shuffle()
            when (valorCarta){
                1 -> if(buttoes[0].text!=listaSelecionadas!![position].correctAnswer)
                    buttoes[0].visibility = View.INVISIBLE
                else
                    buttoes[1].visibility = View.INVISIBLE

                2 -> when(listaSelecionadas!![position].correctAnswer){
                    buttoes[0].text -> {
                        buttoes[1].visibility = View.INVISIBLE
                        buttoes[2].visibility = View.INVISIBLE
                    }
                    buttoes[1].text -> {
                        buttoes[0].visibility = View.INVISIBLE
                        buttoes[2].visibility = View.INVISIBLE
                    }
                    buttoes[2].text -> {
                        buttoes[1].visibility = View.INVISIBLE
                        buttoes[0].visibility = View.INVISIBLE
                    }
                    buttoes[3].text -> {
                        buttoes[1].visibility = View.INVISIBLE
                        buttoes[2].visibility = View.INVISIBLE
                    }
                }
                3 -> for(btn : Button in buttoes){
                    if (listaSelecionadas!![position].correctAnswer != btn.text)
                        btn.visibility = View.INVISIBLE

                }

            }
     }

    private fun updateView(position: Int) {
        if (position >= listaSelecionadas!!.size) {
            irParaResultados()
            return
        }

        btnDesistir.setOnClickListener {
            if (position==0)
                pontuacao = 500
            else
                pontuacao = listaValores[position-1]
            irParaResultados()
        }

        btnTentativaExtra.setOnClickListener {
            btnAjudaCartas.isClickable = false
            btnReferencia.isClickable = false
            btnTentativaExtra.visibility = View.GONE
            tentativaExtra = true
        }
        btnReferencia.setOnClickListener {
            btnAjudaCartas.isClickable = false
            btnTentativaExtra.isClickable = false
            btnReferencia.visibility = View.GONE
            Toast.makeText(context,listaSelecionadas!![position].reference,Toast.LENGTH_LONG).show()
        }

        btnAjudaCartas.setOnClickListener {
            btnReferencia.isClickable = false
            btnTentativaExtra.isClickable = false
            btnAjudaCartas.visibility = View.GONE
            val listaValores = Arrays.asList(0,1,2,3)
            listaValores.shuffle()
            val dialogView = Dialog(context!!)
            dialogView.setContentView(R.layout.cartas)
            dialogView.setCancelable(false)
            dialogView.carta1.setOnClickListener {
                dialogView.carta1.text = listaValores[0].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position,listaValores[0])
                    dialogView.dismiss()
                },1000)
            }
            dialogView.carta2.setOnClickListener {
                dialogView.carta2.text = listaValores[1].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position,listaValores[1])
                    dialogView.dismiss()
                },1000)
            }
            dialogView.carta3.setOnClickListener {
                dialogView.carta3.text = listaValores[2].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position,listaValores[2])
                    dialogView.dismiss()
                },1000)
            }
            dialogView.carta4.setOnClickListener {
                dialogView.carta4.text = listaValores[3].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position,listaValores[3])
                    dialogView.dismiss()
                },1000)
            }
            dialogView.show()
        }

        if (isGameShow) {
            btnAnswer1.visibility = View.VISIBLE
            btnAnswer2.visibility = View.VISIBLE
            btnAnswer3.visibility = View.VISIBLE
            btnAnswer4.visibility = View.VISIBLE
            btnTentativaExtra.isClickable = true
            btnReferencia.isClickable = true
            btnAjudaCartas.isClickable = true
            when (position){

                0 -> txtValoresPerguntas.text = getString(R.string.valor_resultado,listaValores[position],0,0)
                15 -> {
                    txtValoresPerguntas.text = getString(R.string.valor_resultado,listaValores[position],0,0)
                    btnTentativaExtra.visibility = View.GONE
                    btnAjudaCartas.visibility = View.GONE
                    btnReferencia.visibility = View.GONE
                    btnDesistir.visibility = View.GONE
                } else -> txtValoresPerguntas.text = getString(R.string.valor_resultado,listaValores[position],listaValores[position-1],listaValores[position]/4)
            }
            if (!tentativaExtra){
                val dialogView = Dialog(context!!, R.style.mydialog)
                isTempo = true
                dialogView.setContentView(R.layout.proxima_pergunta)
                dialogView.setCancelable(false)
                dialogView.txtValorPergunta.text = getString(R.string.valor_pergunta,listaValores[position])
                YoYo.with(Techniques.FadeIn)
                        .duration(600)
                        .playOn(dialogView.proximaPerguntaView)
                if(position==15)
                    dialogView.btnDesistirFinal.visibility = View.VISIBLE
                dialogView.btnDesistirFinal.setOnClickListener {
                    pontuacao = listaValores[position-1]
                    irParaResultados()
                    dialogView.dismiss()
                }
                dialogView.btnNextQuestion.setOnClickListener {
                    dialogView.btnNextQuestion.isClickable = false
                    YoYo.with(Techniques.FadeOut)
                            .duration(500)
                            .playOn(dialogView.proximaPerguntaView)
                    Handler().postDelayed({
                        YoYo.with(Techniques.FadeIn)
                                .duration(600)
                                .playOn(gameLayout)
                        Handler().postDelayed({
                            dialogView.dismiss()
                            timer = object : CountDownTimer(30000, 1000) {
                                override fun onFinish() {
                                    when (position){
                                        0 -> pontuacao = 0
                                        15 -> {
                                            pontuacao = 0
                                        } else -> pontuacao = listaValores[position]/4
                                    }

                                    irParaResultados()
                                }
                                override fun onTick(p0: Long) {
                                    txtTimerQuiz.text = (p0/1000).toString()
                                }
                            }.start()
                        }, 100)
                    }, 600)
                }
                dialogView.show()
                val window = dialogView.window
                window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
            }
        }

        if(!tentativaExtra){

            btnAnswer1.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.regularAnswer))
            btnAnswer2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.regularAnswer))
            btnAnswer3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.regularAnswer))
            btnAnswer4.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.regularAnswer))

            perguntas.add(listaSelecionadas!![position].correctAnswer)
            perguntas.add(listaSelecionadas!![position].answerB)
            perguntas.add(listaSelecionadas!![position].answerC)
            perguntas.add(listaSelecionadas!![position].answerD)
            perguntas.shuffle()
            TxtQuestion.text = listaSelecionadas!![position].question
            TxtQuestionNumber.text = getString(R.string.pergunta, position + 1)
            btnAnswer1.text = perguntas[0]
            btnAnswer2.text = perguntas[1]
            btnAnswer3.text = perguntas[2]
            btnAnswer4.text = perguntas[3]
            perguntas.clear()
        }



        btnAnswer1.setOnClickListener {
            definirCertoErrado(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, listaSelecionadas!![position].correctAnswer, position)
        }
        btnAnswer2.setOnClickListener {
            definirCertoErrado(btnAnswer2, btnAnswer1, btnAnswer3, btnAnswer4, listaSelecionadas!![position].correctAnswer, position)
        }
        btnAnswer3.setOnClickListener {
            definirCertoErrado(btnAnswer3, btnAnswer2, btnAnswer1, btnAnswer4, listaSelecionadas!![position].correctAnswer, position)
        }
        btnAnswer4.setOnClickListener {
            definirCertoErrado(btnAnswer4, btnAnswer2, btnAnswer3, btnAnswer1, listaSelecionadas!![position].correctAnswer, position)
        }
        tentativaExtra=false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isTempo)
            timer.cancel()
    }
}
