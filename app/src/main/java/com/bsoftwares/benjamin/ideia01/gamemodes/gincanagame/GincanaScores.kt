package com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame


import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable

import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_gincana_scores.*
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.pergunta_gincana.*
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GincanaScores : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gincana_scores, container, false)
    }

    var todosRespondem = false
    private var times: java.util.ArrayList<Time>? = java.util.ArrayList()
    private var listaDePerguntas: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    private var pKids: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    private var pFacil: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    private var pMedia: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    private var pDificil: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    private var pRespondidas: java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    private var txtViews: ArrayList<TextView> = ArrayList()
    private var rbViews: ArrayList<RadioButton> = ArrayList()
    var pontos: java.util.ArrayList<Int>? = java.util.ArrayList()
    private var position = 1
    private var atualDificuldade = ""
    private var atualTime = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            todosRespondem = arguments!!.getBoolean("umOuTodos")
            times = arguments!!.getParcelableArrayList("Times")
            listaDePerguntas = arguments!!.getParcelableArrayList("PerguntasSelecionadas")
            pontos = arguments!!.getIntegerArrayList("Pontos")
        }

        if (!todosRespondem) {
            rgTimesButtons.visibility = View.VISIBLE
        }

        txtViews.add(txtTeam01Points)
        txtViews.add(txtTeam02Points)
        txtViews.add(txtTeam03Points)
        txtViews.add(txtTeam04Points)

        rbViews.add(rbTeam01)
        rbViews.add(rbTeam02)
        rbViews.add(rbTeam03)
        rbViews.add(rbTeam04)


        txtKidsPoints.text = getString(R.string.pontuacaoTime, getString(R.string.kids), pontos!![0])
        txtFacilPoints.text = getString(R.string.pontuacaoTime, getString(R.string.f_cil), pontos!![1])
        txtMedioPoints.text = getString(R.string.pontuacaoTime, getString(R.string.m_dio), pontos!![2])
        txtDificilPoints.text = getString(R.string.pontuacaoTime, getString(R.string.dif_cil), pontos!![3])

        separarPerguntas()

        for (i in 0..times!!.size - 1) {
            txtViews[i].setOnLongClickListener {
                val alertDialog = AlertDialog.Builder(context).create()
                alertDialog.setTitle(times!![i].nome)
                alertDialog.setMessage(getString(R.string.ajustar_pontos))
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL,
                    "Cancelar"
                ) { dialog, which -> alertDialog.dismiss() }
                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE,
                    "Adicionar 10"
                ) { dialog, which -> times!![i].pontosAjuste+= 10
                updateView(position)}
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE,
                    "Remover 10"
                ) { dialog, which -> times!![i].pontosAjuste-= 10
                    if(times!![i].pontosAjuste<0)
                        times!![i].pontosAjuste = 0
                    updateView(position) }
                alertDialog.show()
                true
            }
            txtViews[i].text =
                    getString(R.string.pontuacaoTime, times!![i].nome, times!![i].pontuacao)
            if (i > 2) {
                txtTeam04Points.visibility = View.VISIBLE
                rbTeam04.visibility = View.VISIBLE
            } else {
                if (i > 1) {
                    txtTeam03Points.visibility = View.VISIBLE
                    rbTeam03.visibility = View.VISIBLE
                }
            }
            rbViews[i].text = times!![i].nome
        }

        btnAjuda.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle(getString(R.string.ajuda))
            if (todosRespondem)
                alertDialog.setMessage(getString(R.string.ajudaTxtTodosRespondem))
            else
                alertDialog.setMessage(getString(R.string.ajudaTxtCadaTime))
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                "OK"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()
        }

        btnGincanaScoresNext.setOnClickListener {
            if (isEverythingChecked()) {

                val alertDialog = AlertDialog.Builder(context).create()
                alertDialog.setTitle(getString(R.string.confirmacao))

                val idRbSelecionadoDificuldade =
                    rgPerguntasTipos.findViewById<RadioButton>(rgPerguntasTipos.checkedRadioButtonId)
                val posicaoRbSelecionadoDificuldade =
                    rgPerguntasTipos.indexOfChild(idRbSelecionadoDificuldade)
                val rbSelecionadoDificuldade =
                    rgPerguntasTipos.getChildAt(posicaoRbSelecionadoDificuldade) as RadioButton
                atualDificuldade = rbSelecionadoDificuldade.text.toString()
                if (todosRespondem) {
                    alertDialog.setMessage("Você confirma a pergunta de dificuldade: " + atualDificuldade)
                    rgPerguntasTipos.clearCheck()
                } else {
                    val idRbSelecionadoTime =
                        rgTimesButtons.findViewById<RadioButton>(rgTimesButtons.checkedRadioButtonId)
                    val posicaoRbSelecionadoTime = rgTimesButtons.indexOfChild(idRbSelecionadoTime)
                    val rbSelecionadoTime =
                        rgTimesButtons.getChildAt(posicaoRbSelecionadoTime) as RadioButton
                    atualTime = rbSelecionadoTime.text.toString()
                    alertDialog.setMessage("Você confirma a pergunta de\nDificuldade: " + atualDificuldade + "\nPara o time: " + atualTime)
                    rgPerguntasTipos.clearCheck()
                    rgTimesButtons.clearCheck()
                }
                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE,
                    "OK"
                ) { dialog, which -> gerarPergunta() }
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE,
                    "Fechar"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.show()
            }
        }
        updateView(position)
    }

    private fun gerarPergunta() {
        val perguntaView = Dialog(context, R.style.mydialog)
        perguntaView.setContentView(R.layout.pergunta_gincana)
        perguntaView.setCancelable(false)
        perguntaView.txtNpergunta.text = getString(R.string.pergunta, position)

        perguntaView.txtDificuldadeGincana.text = atualDificuldade

        if (!todosRespondem) {
            perguntaView.txtQuemRespondeu.visibility = View.GONE
            perguntaView.txtTimeGinacana.text = atualTime
        }

        if (atualDificuldade == "Kids") {
            definirPergunta(pKids, perguntaView, pontos!![0])
        }
        if (atualDificuldade == "Fácil") {
            definirPergunta(pFacil, perguntaView, pontos!![1])
        }
        if (atualDificuldade == "Médio") {
            definirPergunta(pMedia, perguntaView, pontos!![2])
        }
        if (atualDificuldade == "Difícil") {
            definirPergunta(pDificil, perguntaView, pontos!![3])
        }
        perguntaView.show()
        val window = perguntaView.window
        window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            perguntaView.txtPerguntaGincana.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,40,5,2)
            perguntaView.btnResposta01.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
            perguntaView.btnResposta02.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
            perguntaView.btnResposta03.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
            perguntaView.btnResposta04.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
            perguntaView.txtOption1.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
            perguntaView.txtOption2.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
            perguntaView.txtOption3.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
            perguntaView.txtOption4.setAutoSizeTextTypeUniformWithConfiguration(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,25,5,2)
        }
    }

    private fun definirPergunta(
        perguntas: java.util.ArrayList<QuestionParcelable>?,
        perguntaView: Dialog,
        pontos: Int
    ) {
        perguntaView.txtPerguntaGincana.text = perguntas!![0].question
        perguntaView.btn_referencia.setOnClickListener {
            Toast.makeText(context, perguntas[0].reference, Toast.LENGTH_LONG).show()
        }
        val checkBoxes = Arrays.asList(
            perguntaView.btnResposta01,
            perguntaView.btnResposta02,
            perguntaView.btnResposta03,
            perguntaView.btnResposta04
        )
        if (todosRespondem) {
            val opcoes = Arrays.asList(
                perguntas[0].correctAnswer,
                perguntas[0].answerB,
                perguntas[0].answerC,
                perguntas[0].answerD
            )
            perguntaView.rgOpcoes.visibility = View.VISIBLE
            opcoes.shuffle()
            perguntaView.txtOption1.text = getString(R.string.opcaoX, "a", opcoes[0])
            perguntaView.txtOption2.text = getString(R.string.opcaoX, "b", opcoes[1])
            perguntaView.txtOption3.text = getString(R.string.opcaoX, "c", opcoes[2])
            perguntaView.txtOption4.text = getString(R.string.opcaoX, "d", opcoes[3])


            if (opcoes[0] == perguntas[0].correctAnswer)
                perguntaView.txtOption1.setBackgroundResource(R.drawable.selected)

            if (opcoes[1] == perguntas[0].correctAnswer)
                perguntaView.txtOption2.setBackgroundResource(R.drawable.selected)

            if (opcoes[2] == perguntas[0].correctAnswer)
                perguntaView.txtOption3.setBackgroundResource(R.drawable.selected)

            if (opcoes[3] == perguntas[0].correctAnswer)
                perguntaView.txtOption4.setBackgroundResource(R.drawable.selected)

            for (i in 0..times!!.size - 1) {
                checkBoxes[i].text = times!![i].nome
                checkBoxes[i].visibility = View.VISIBLE
            }

            perguntaView.btnChecarScores.setOnClickListener {
                val alertDialog = AlertDialog.Builder(context).create()
                alertDialog.setTitle(getString(R.string.confirmacao))
                alertDialog.setMessage(getString(R.string.confirmarTime))
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim") { dialog, which ->
                    perguntaView.dismiss()
                    for (i in 0..times!!.size - 1) {
                        if (checkBoxes[i].isChecked) {
                            perguntas[0].timesAcertaram.add(times!![i].nome)
                            times!![i].pontuacao += pontos
                        }
                        checkBoxes[i].text = times!![i].nome
                        checkBoxes[i].visibility = View.VISIBLE
                    }
                    pRespondidas!!.add(perguntas[0])
                    perguntas.removeAt(0)
                    updateView(position++)
                }
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE,
                    "Não"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.show()
            }
        } else {
            val opcoes = Arrays.asList(
                perguntas[0].correctAnswer,
                perguntas[0].answerB,
                perguntas[0].answerC,
                perguntas[0].answerD
            )
            opcoes.shuffle()
            var respostaSelecionado = ""
            for (i in 0..checkBoxes.size - 1) {
                checkBoxes[i].visibility = View.VISIBLE
                checkBoxes[i].text = opcoes[i]
                checkBoxes[i].setOnClickListener {
                    respostaSelecionado = checkBoxes[i].text.toString()
                    for (j in 0..checkBoxes.size - 1) {
                        checkBoxes[j].isChecked = checkBoxes[i] == checkBoxes[j]
                    }
                }
            }
            perguntaView.btnChecarScores.setOnClickListener {
                if (respostaSelecionado == "") {
                    Toast.makeText(
                        context,
                        "É necessário escolhar uma resposta",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val alertDialog = AlertDialog.Builder(context).create()
                    alertDialog.setTitle(getString(R.string.confirmacao))
                    alertDialog.setMessage(getString(R.string.confirmarTime))
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim") { dialog, which ->
                        perguntaView.dismiss()
                        for (time: Time in times!!) {
                            if (time.nome == atualTime) {
                                perguntas[0].timesAcertaram.add(time.nome)
                                if (perguntas[0].correctAnswer == respostaSelecionado)
                                    time.pontuacao += pontos
                            }
                        }
                        perguntas[0].selectedAnswer = respostaSelecionado
                        pRespondidas!!.add(perguntas[0])
                        perguntas.removeAt(0)
                        updateView(position++)
                    }
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEGATIVE,
                        "Não"
                    ) { dialog, which -> dialog.dismiss() }
                    alertDialog.show()
                }

            }
        }

    }

    private fun isEverythingChecked(): Boolean {
        if (rgPerguntasTipos.checkedRadioButtonId == -1) {
            Toast.makeText(context, "Falta escolher a dificuldade", Toast.LENGTH_LONG).show()
            return false
        }
        if (!todosRespondem) {
            if (rgTimesButtons.checkedRadioButtonId == -1) {
                Toast.makeText(context, "Falta escolher o time", Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true
    }

    private fun separarPerguntas() {
        for (pergunta: QuestionParcelable in listaDePerguntas!!) {
            if (pergunta.dificulty == "K")
                pKids!!.add(pergunta)
            if (pergunta.dificulty == "F")
                pFacil!!.add(pergunta)
            if (pergunta.dificulty == "M")
                pMedia!!.add(pergunta)
            if (pergunta.dificulty == "D")
                pDificil!!.add(pergunta)
        }
        pKids!!.shuffle()
        pFacil!!.shuffle()
        pMedia!!.shuffle()
        pDificil!!.shuffle()
    }

    fun updateView(rodada: Int) {
        for (i in 0..times!!.size - 1) {
            times!![i].pontuacao = 0
        }

        for (perguntaRespondida: QuestionParcelable in pRespondidas!!) {
            for (timesResponderam: String in perguntaRespondida.timesAcertaram) {
                for (time: Time in times!!) {
                    if (time.nome == timesResponderam) {
                        if (perguntaRespondida.dificulty == "K")
                            time.pontuacao += pontos!![0]
                        if (perguntaRespondida.dificulty == "F")
                            time.pontuacao += pontos!![1]
                        if (perguntaRespondida.dificulty == "M")
                            time.pontuacao += pontos!![2]
                        if (perguntaRespondida.dificulty == "D")
                            time.pontuacao += pontos!![3]
                    }
                }
            }
        }

        for (i in 0..times!!.size - 1) {
            txtViews[i].text =
                    getString(R.string.pontuacaoTime, times!![i].nome, times!![i].pontuacao + times!![i].pontosAjuste)
        }
        if (!pRespondidas!!.isEmpty()) {
            rvResultadoAtual.layoutManager = LinearLayoutManager(context)
            rvResultadoAtual.adapter = GincanaResultsAdapter(pRespondidas!!, times!!, this)
            /*rvResultadoAtual.getViewTreeObserver()
                    .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            for (i in 0.. times!!.size-1){
                                txtViews[i].text = getString(R.string.pontuacaoTime,times!![i].nome,times!![i].pontuacao)
                            }
                            rvResultadoAtual.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                        }
                    })*/
        }



        txtNrodada.text = getString(R.string.nRodada, rodada)
        if (pKids!!.isEmpty())
            rbPerguntaKids.isClickable = false
        if (pFacil!!.isEmpty())
            rbPerguntaKids.isClickable = false
        if (pKids!!.isEmpty())
            rbPerguntaKids.isClickable = false
        if (pKids!!.isEmpty())
            rbPerguntaKids.isClickable = false
    }

}
