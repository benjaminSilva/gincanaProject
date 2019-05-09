package com.bsoftwares.benjamin.ideia01.gamemodes.quizgame


import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.*
import com.bsoftwares.benjamin.ideia01.R.id.gameLayout
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.cartas.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_gincana_scores.*
import kotlinx.android.synthetic.main.proxima_pergunta.*
import java.text.BreakIterator
import java.util.*
import kotlin.collections.ArrayList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GameFragment : Fragment() {


    //Variáveis
    val resultsFragment: Fragment = ResultsFragment()
    var perguntas: ArrayList<String> = ArrayList()
    var pontuacao = 0
    lateinit var timer: CountDownTimer
    var isTempo = false
    var isGameShow = false
    var tentativaExtra = false
    var position = 0
    var tempo = 0.toLong()
    var onResume = false
    var listaSelecionadas: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()

    val listaValores: IntArray = intArrayOf(1000, 2000, 3000, 4000, 5000, 10000, 20000, 30000, 40000, 50000, 100000, 200000, 300000, 400000, 500000, 1000000)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            if (arguments != null) {
                listaSelecionadas = arguments?.getParcelableArrayList("PerguntasSelecionadas")
                if (arguments!!.containsKey("isGameShow")) {
                    isGameShow = true
                } else {
                    btnTentativaExtra.visibility = View.GONE
                    btnAjudaCartas.visibility = View.GONE
                    btnReferencia.visibility = View.GONE
                    btnDesistir.visibility = View.GONE
                    btn_acertar.visibility = View.INVISIBLE
                    btn_errar.visibility = View.INVISIBLE
                    btn_desistir.visibility = View.INVISIBLE
                }
                isTempo = arguments!!.getBoolean("tempoParaPergunta")
                if (isTempo && !isGameShow) {
                    tempo = listaSelecionadas!!.size.toLong()*10
                    setTimer(tempo)
                }
                updateView(0, listaSelecionadas)
            }
        }
    }

    fun setTimer(time : Long){
        timer = object : CountDownTimer(  time * 1000, 1000) {
            override fun onFinish() {
                irParaResultados(listaSelecionadas)
                return
            }

            override fun onTick(p0: Long) {
                tempo = p0/1000
                txtTimerQuiz.text = (p0 / 1000).toString()
            }
        }.start()
    }

    fun desbilitarBotoes() {
        btnAnswer1.isClickable = false
        btnAnswer2.isClickable = false
        btnAnswer3.isClickable = false
        btnAnswer4.isClickable = false
        btnTentativaExtra.isClickable = false
        btnAjudaCartas.isClickable = false
        btnReferencia.isClickable = false
        btnDesistir.isClickable = false
    }

    fun habilitarBotoes() {
        btnAnswer1.isClickable = true
        btnAnswer2.isClickable = true
        btnAnswer3.isClickable = true
        btnAnswer4.isClickable = true
        btnTentativaExtra.isClickable = true
        btnAjudaCartas.isClickable = true
        btnReferencia.isClickable = true
        btnDesistir.isClickable = true
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

    fun irParaResultados(listaPerguntas: ArrayList<QuestionParcelable>?) {
        desbilitarBotoes()
        val bundle = Bundle()
        bundle.putParcelableArrayList("PerguntasSelecionadas", listaPerguntas)
        resultsFragment.arguments = bundle
        if (isTempo){
            timer.cancel()
            isTempo = false
        }
        if (isGameShow){
            bundle.putInt("pontuacao", pontuacao)
            bundle.putString("Jogador",arguments!!.getString("Jogador",""))
        }
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameGame, resultsFragment,"GameResultado").commit()
        /*Handler().postDelayed({
            habilitarBotoes()
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }, 1000)*/

    }

    fun definirCertoErrado(escolhido: Button, outro1: Button, outro2: Button, outro3: Button, resposta: String, pos: Int, listaPerguntas: ArrayList<QuestionParcelable>?) {
        desbilitarBotoes()
        listaPerguntas!![pos].selectedAnswer = escolhido.text.toString()
        var isCerto = false
        if (escolhido.text == resposta) {
            isCerto = true
            if (isGameShow)
                pontuacao = listaValores[pos]
            if (tentativaExtra)
                tentativaExtra = false
            escolhido.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
        } else {
            escolhido.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.wrongAnswer))
            if (!tentativaExtra)
                if (!isGameShow)
                    when (resposta) {
                        outro1.text -> outro1.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
                        outro2.text -> outro2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
                        outro3.text -> outro3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.correctAnswer))
                    }
            if (tentativaExtra) {
                updateView(pos, listaPerguntas)
                return
            }
            if(isGameShow){
                when (pos) {
                    0 -> pontuacao = 0
                    15 -> {
                        pontuacao = 0
                    }
                    else -> pontuacao = listaValores[pos] / 4
                }
            }

        }

        if ((pos + 1) < listaPerguntas.size && !isGameShow)
            animateAllViews(escolhido, outro1, outro2, outro3, TxtQuestion, Techniques.FadeOut, 500)

        if ((pos + 1) < listaPerguntas.size && isGameShow)
            YoYo.with(Techniques.FadeOut)
                    .duration(700)
                    .playOn(gameLayout)

        if (isGameShow) {
            timer.cancel()
            isTempo = false
        }

        Handler().postDelayed({
            if (isGameShow && !isCerto) {
                irParaResultados(listaPerguntas)
            } else {
                updateView(pos + 1, listaPerguntas)
                if ((pos + 1) < listaPerguntas.size) {
                    animateAllViews(escolhido, outro1, outro2, outro3, TxtQuestion, Techniques.FadeIn, 500)
                    habilitarBotoes()
                }
            }
        }, 700)

    }

    fun tratarResultadoCarta(position: Int, valorCarta: Int, listaPerguntas: ArrayList<QuestionParcelable>?) {
        val buttoes = Arrays.asList(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4)
        buttoes.shuffle()
        when (valorCarta) {
            1 -> if (buttoes[0].text != listaPerguntas!![position].correctAnswer)
                buttoes[0].visibility = View.INVISIBLE
            else
                buttoes[1].visibility = View.INVISIBLE

            2 -> when (listaPerguntas!![position].correctAnswer) {
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
            3 -> for (btn: Button in buttoes) {
                if (listaPerguntas!![position].correctAnswer != btn.text)
                    btn.visibility = View.INVISIBLE

            }

        }
    }

    private fun updateView(position: Int, listaPerguntas: ArrayList<QuestionParcelable>?) {
        if (position >= listaPerguntas!!.size) {
            irParaResultados(listaPerguntas)
            return
        }

        btnDesistir.setOnClickListener {
            if (position == 0)
                pontuacao = 0
            else
                pontuacao = listaValores[position - 1]
            irParaResultados(listaPerguntas)
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
            timer.cancel()
            timer = object : CountDownTimer(100000, 1000) {
                override fun onFinish() {
                    when (position) {
                        0 -> pontuacao = 0
                        else -> pontuacao = listaValores[position] / 4
                    }
                    irParaResultados(listaPerguntas)
                }

                override fun onTick(p0: Long) {
                    tempo = p0/1000
                    txtTimerQuiz.text = (p0 / 1000).toString()
                }
            }.start()
            Toast.makeText(context, listaPerguntas[position].reference, Toast.LENGTH_LONG).show()
        }

        btnAjudaCartas.setOnClickListener { it ->
            btnReferencia.isClickable = false
            btnTentativaExtra.isClickable = false
            btnAjudaCartas.visibility = View.GONE
            val listaValores = Arrays.asList(0, 1, 2, 3)
            listaValores.shuffle()
            val dialogView = Dialog(context!!)
            dialogView.setContentView(R.layout.cartas)
            dialogView.setCancelable(false)
            dialogView.carta1.setOnClickListener {
                dialogView.carta1.text = listaValores[0].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position, listaValores[0], listaPerguntas)
                    dialogView.dismiss()
                }, 1000)
            }
            dialogView.carta2.setOnClickListener {
                dialogView.carta2.text = listaValores[1].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position, listaValores[1], listaPerguntas)
                    dialogView.dismiss()
                }, 1000)
            }
            dialogView.carta3.setOnClickListener {
                dialogView.carta3.text = listaValores[2].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position, listaValores[2], listaPerguntas)
                    dialogView.dismiss()
                }, 1000)
            }
            dialogView.carta4.setOnClickListener {
                dialogView.carta4.text = listaValores[3].toString()
                Handler().postDelayed({
                    tratarResultadoCarta(position, listaValores[3], listaPerguntas)
                    dialogView.dismiss()
                }, 1000)
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
            when (position) {

                0 -> {
                    btn_acertar.text = getString(R.string.acertar, listaValores[position])
                    btn_errar.text = getString(R.string.errar,0)
                    btn_desistir.text = getString(R.string.Desistir,0)
                }

                15 -> {
                    btn_acertar.text = getString(R.string.acertar, listaValores[position])
                    btn_errar.text = getString(R.string.errar,0)
                    btn_desistir.text = getString(R.string.Desistir,0)
                    btnTentativaExtra.visibility = View.GONE
                    btnAjudaCartas.visibility = View.GONE
                    btnReferencia.visibility = View.GONE
                    btnDesistir.visibility = View.GONE
                }
                else ->{
                    btn_acertar.text = getString(R.string.acertar, listaValores[position])
                    btn_errar.text = getString(R.string.errar,listaValores[position] / 4)
                    btn_desistir.text = getString(R.string.Desistir,listaValores[position - 1])
                }
            }
            if (!tentativaExtra) {
                val dialogView = Dialog(context!!, R.style.mydialog)
                dialogView.setContentView(R.layout.proxima_pergunta)
                dialogView.setCancelable(false)
                dialogView.txtValorPergunta.text = getString(R.string.valor_pergunta )
                dialogView.txtViewValor.text = getString(R.string.pergunta_valor,listaValores[position])
                YoYo.with(Techniques.FadeIn)
                        .duration(500)
                        .playOn(dialogView.proximaPerguntaView)
                if (position == 15)
                    dialogView.btnDesistirFinal.visibility = View.VISIBLE
                dialogView.btnDesistirFinal.setOnClickListener {
                    pontuacao = listaValores[position - 1]
                    irParaResultados(listaPerguntas)
                    dialogView.dismiss()
                }
                dialogView.btnNextQuestion.setOnClickListener {
                    isTempo = true
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
                                    when (position) {
                                        0 -> pontuacao = 0
                                        15 -> {
                                            pontuacao = 0
                                        }
                                        else -> pontuacao = listaValores[position] / 4
                                    }
                                    irParaResultados(listaPerguntas)
                                }

                                override fun onTick(p0: Long) {
                                    tempo = p0/1000
                                    txtTimerQuiz.text = (p0 / 1000).toString()
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

        if (!tentativaExtra) {

            btnAnswer1.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.white))
            btnAnswer2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.white))
            btnAnswer3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.white))
            btnAnswer4.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.white))

            perguntas.add(listaPerguntas[position].correctAnswer)
            perguntas.add(listaPerguntas[position].answerB)
            perguntas.add(listaPerguntas[position].answerC)
            perguntas.add(listaPerguntas[position].answerD)
            perguntas.shuffle()
            TxtQuestion.text = listaPerguntas[position].question
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                TxtQuestion.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,30,5,2)
                btnAnswer1.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,20,5,2)
                btnAnswer2.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,20,5,2)
                btnAnswer3.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,20,5,2)
                btnAnswer4.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,20,5,2)
                btnAjudaCartas.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,16,5,2)
                btnTentativaExtra.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,16,5,2)
                btnReferencia.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,16,5,2)
                btn_acertar.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,16,5,2)
                btn_desistir.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,16,5,2)
                btn_errar.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,16,5,2)
            }
            txtQuestionNumber.text = getString(R.string.pergunta,(position + 1))
            btnAnswer1.text = perguntas[0]
            btnAnswer2.text = perguntas[1]
            btnAnswer3.text = perguntas[2]
            btnAnswer4.text = perguntas[3]
            perguntas.clear()
        }



        btnAnswer1.setOnClickListener {
            definirCertoErrado(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, listaPerguntas[position].correctAnswer, position, listaPerguntas)
        }
        btnAnswer2.setOnClickListener {
            definirCertoErrado(btnAnswer2, btnAnswer1, btnAnswer3, btnAnswer4, listaPerguntas[position].correctAnswer, position, listaPerguntas)
        }
        btnAnswer3.setOnClickListener {
            definirCertoErrado(btnAnswer3, btnAnswer2, btnAnswer1, btnAnswer4, listaPerguntas[position].correctAnswer, position, listaPerguntas)
        }
        btnAnswer4.setOnClickListener {
            definirCertoErrado(btnAnswer4, btnAnswer2, btnAnswer3, btnAnswer1, listaPerguntas[position].correctAnswer, position, listaPerguntas)
        }
        tentativaExtra = false
    }

    fun getCurrentPergunta (perguntas : ArrayList<QuestionParcelable>) : Int {
        var count = 0
        for (pergunta : QuestionParcelable in perguntas){
            if (pergunta.selectedAnswer != ""){
                count++
            }
        }
        return count
    }

    override fun onPause() {
        super.onPause()
        onResume = true
        if (isTempo){
            timer.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        if (onResume)
            if (isTempo)
                setTimer(tempo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isTempo){
            timer.cancel()
            isTempo = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("perguntasSalvas", listaSelecionadas)
        if (isTempo) {
            outState.putLong("salvarTempo", tempo)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if(savedInstanceState!=null){
            if (savedInstanceState.containsKey("salvarTempo")) {
                tempo = savedInstanceState.getLong("salvarTempo")
                setTimer(tempo)
            }
            val perguntas = savedInstanceState.getParcelableArrayList<QuestionParcelable>("perguntasSalvas")
            updateView(getCurrentPergunta(perguntas),perguntas)
        }
    }

}
