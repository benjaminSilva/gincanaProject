package com.bsoftwares.benjamin.ideia01.gamemodes.quizgame


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame.Time
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.android.synthetic.main.ranking_layout.view.*
import java.text.DecimalFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    var listaSelecionadas: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaRespondidas: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRetorna.isClickable = false

        YoYo.with(Techniques.SlideInLeft)
            .duration(1000)
            .playOn(clResults)
        Handler().postDelayed({
            btnRetorna.isClickable = true
        }, 1000)
        val decimalFormat = DecimalFormat("###.#")
        listaSelecionadas = arguments!!.getParcelableArrayList("PerguntasSelecionadas")
        val nCorretas = getCorretas(listaSelecionadas!!)
        if (arguments!!.containsKey("pontuacao")) {
            txtResultado.text =
                    getString(R.string.resultado_final_game_show, arguments!!.getInt("pontuacao"))
        } else {
            txtResultado.text = getString(
                R.string.pontuacao,
                nCorretas,
                listaSelecionadas!!.size,
                decimalFormat.format(nCorretas.toFloat() / listaSelecionadas!!.size.toFloat() * 100)
            )
            btnRanking.visibility = View.GONE
        }

        val resultados: ArrayList<Time> = ArrayList()

        if (arguments!!.containsKey("pontuacao")) {

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            if (sharedPref!!.contains("PrimeiroNome")) {
                val jogador = Time()
                jogador.nome = sharedPref.getString("PrimeiroNome", "Time aleatório")
                jogador.pontuacao = sharedPref.getInt("PrimeiroPonto", 0)
                resultados.add(jogador)
            }
            if (sharedPref.contains("SegundoNome")) {
                val jogador = Time()
                jogador.nome = sharedPref.getString("SegundoNome", "Time aleatório")
                jogador.pontuacao = sharedPref.getInt("SegundoPonto", 0)
                resultados.add(jogador)
            }
            if (sharedPref.contains("TerceiroNome")) {
                val jogador = Time()
                jogador.nome = sharedPref.getString("TerceiroNome", "Time aleatório")
                jogador.pontuacao = sharedPref.getInt("TerceiroPonto", 0)
                resultados.add(jogador)
            }
            if (sharedPref.contains("QuartoNome")) {
                val jogador = Time()
                jogador.nome = sharedPref.getString("QuartoNome", "Time aleatório")
                jogador.pontuacao = sharedPref.getInt("QuartoPonto", 0)
                resultados.add(jogador)
            }
            if (sharedPref.contains("QuintoNome")) {
                val jogador = Time()
                jogador.nome = sharedPref.getString("QuintoNome", "Time aleatório")
                jogador.pontuacao = sharedPref.getInt("QuintoPonto", 0)
                resultados.add(jogador)
            }
            if (arguments!!.getString("Jogador", "") != "") {
                val jogadorParaSubstituir = Time()
                jogadorParaSubstituir.nome = arguments!!.getString("Jogador")
                jogadorParaSubstituir.pontuacao = arguments!!.getInt("pontuacao")
                val aux = Time()
                aux.nome = jogadorParaSubstituir.nome
                aux.pontuacao = jogadorParaSubstituir.pontuacao
                for (jogador: Time in resultados) {
                    if (jogador.pontuacao <= jogadorParaSubstituir.pontuacao) {
                        aux.nome = jogador.nome
                        aux.pontuacao = jogador.pontuacao
                        jogador.pontuacao = jogadorParaSubstituir.pontuacao
                        jogador.nome = jogadorParaSubstituir.nome
                        jogadorParaSubstituir.nome = aux.nome
                        jogadorParaSubstituir.pontuacao = aux.pontuacao
                    }
                }
                if (resultados.size < 5)
                    resultados.add(aux)

                if (resultados.size > 0) {
                    with(sharedPref.edit()) {
                        putString("PrimeiroNome", resultados[0].nome)
                        putInt("PrimeiroPonto", resultados[0].pontuacao)
                        apply()
                    }
                }
                if (resultados.size > 1) {
                    with(sharedPref.edit()) {
                        putString("SegundoNome", resultados[1].nome)
                        putInt("SegundoPonto", resultados[1].pontuacao)
                        apply()
                    }
                }
                if (resultados.size > 2) {
                    with(sharedPref.edit()) {
                        putString("TerceiroNome", resultados[2].nome)
                        putInt("TerceiroPonto", resultados[2].pontuacao)
                        apply()
                    }
                }
                if (resultados.size > 3) {
                    with(sharedPref.edit()) {
                        putString("QuartoNome", resultados[3].nome)
                        putInt("QuartoPonto", resultados[3].pontuacao)
                        apply()
                    }
                }
                if (resultados.size > 4) {
                    with(sharedPref.edit()) {
                        putString("QuintoNome", resultados[4].nome)
                        putInt("QuintoPonto", resultados[4].pontuacao)
                        apply()
                    }
                }
            }
        }

        /*if (sharedPref!!.contains("PrimeiroNome")){
            val time = Time()
            time.nome = sharedPref.getString("PrimeiroNome","Time aleatório")
            time.pontuacao = sharedPref.getInt("PrimeiroPonto",0)
            resultados.add(time)
        }
        if (sharedPref.contains("SegundoNome")){
            val time = Time()
            time.nome = sharedPref.getString("SegundoNome","Time aleatório")
            time.pontuacao = sharedPref.getInt("SegundoPonto",0)
            resultados.add(time)
        }
        if (sharedPref.contains("TerceiroNOme")){
            val time = Time()
            time.nome = sharedPref.getString("TerceiroNOme","Time aleatório")
            time.pontuacao = sharedPref.getInt("TerceiroPonto",0)
            resultados.add(time)
        }
        if (sharedPref.contains("QuartoNome")){
            val time = Time()
            time.nome = sharedPref.getString("QuartoNome","Time aleatório")
            time.pontuacao = sharedPref.getInt("QuartoPonto",0)
            resultados.add(time)
        }
        if (sharedPref.contains("QuintoNome")){
            val time = Time()
            time.nome = sharedPref.getString("QuintoNome","Time aleatório")
            time.pontuacao = sharedPref.getInt("QuintoPonto",0)
            resultados.add(time)
        }
        for (jogador : Time in resultados){
            Toast.makeText(context,jogador.nome + jogador.pontuacao, Toast.LENGTH_SHORT).show()
        }*/

        btnRanking.setOnClickListener { it ->
            val dialogView = activity!!.layoutInflater.inflate(R.layout.ranking_layout, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(dialogView)
            val regras = builder.create()
            dialogView.rvRanking.layoutManager = LinearLayoutManager(context)
            dialogView.rvRanking.adapter = RankingAdapter(resultados)
            dialogView.btnOkRanking.setOnClickListener { regras.dismiss() }
            regras.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            regras.show()
        }

        rvResultados.layoutManager = LinearLayoutManager(context)
        rvResultados.adapter = ResultsAdapter(listaRespondidas!!, context!!)
        btnRetorna.setOnClickListener {
            activity!!.finish()
        }
    }

    fun getCorretas(listaSel: ArrayList<QuestionParcelable>): Int {
        var corretas = 0
        for (pergunta: QuestionParcelable in listaSel) {
            if (pergunta.selectedAnswer != "")
                listaRespondidas!!.add(pergunta)
            if (pergunta.selectedAnswer == pergunta.correctAnswer)
                corretas++
        }
        return corretas
    }


}
