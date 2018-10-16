package com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.resultgincanalayout.view.*
import java.util.*
import kotlin.collections.ArrayList

class GincanaResultsAdapter(val resultado: ArrayList<QuestionParcelable>, val times: java.util.ArrayList<Time>?, val gincana : GincanaScores) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.resultgincanalayout, parent, false)
        return CustomViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        return resultado.count()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.txtQuestionNumberResultGincana.text = gincana.context!!.getString(R.string.pergunta, position + 1)
        holder.view.txtPerguntaGincana.text = resultado[position].question
        val timesbtns = Arrays.asList(holder.view.timeResultsGincana01, holder.view.timeResultsGincana02, holder.view.timeResultsGincana03, holder.view.timeResultsGincana04)

        if (gincana.todosRespondem) {
            holder.view.txtDificuldadeResults.text = resultado[position].dificulty
            for (i in 0..times!!.size - 1) {
                timesbtns[i].text = times[i].nome
                timesbtns[i].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(gincana.context!!, R.color.wrongAnswer))
                for (j in 0..resultado[position].timesAcertaram.size - 1) {
                    if (resultado[position].timesAcertaram[j] == times[i].nome) {
                        timesbtns[i].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(gincana.context!!, R.color.correctAnswer))
                        if (resultado[position].dificulty == "K")
                            times[i].pontuacao += gincana.pontos!![0]
                        if (resultado[position].dificulty == "F")
                            times[i].pontuacao += gincana.pontos!![1]
                        if (resultado[position].dificulty == "M")
                            times[i].pontuacao += gincana.pontos!![2]
                        if (resultado[position].dificulty == "D")
                            times[i].pontuacao += gincana.pontos!![3]
                    }
                }

                timesbtns[i].visibility = View.VISIBLE
            }
        } else {
            holder.view.txtDificuldadeResults.text = resultado[position].timesAcertaram[0] + " - " + resultado[position].dificulty
            if (resultado[position].selectedAnswer != resultado[position].correctAnswer) {
                holder.view.txtQuestionNumberResultGincana.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_down_black_24dp, 0)
                timesbtns[0].text = resultado[position].selectedAnswer
                timesbtns[0].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(gincana.context!!, R.color.wrongAnswer))
                timesbtns[1].text = resultado[position].correctAnswer
                timesbtns[1].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(gincana.context!!, R.color.correctAnswer))
            } else {
                for(time : Time in times!!){
                    if(time.nome == resultado[position].timesAcertaram[0]){
                        if (resultado[position].dificulty == "K")
                            time.pontuacao += gincana.pontos!![0]
                        if (resultado[position].dificulty == "F")
                            time.pontuacao += gincana.pontos!![1]
                        if (resultado[position].dificulty == "M")
                            time.pontuacao += gincana.pontos!![2]
                        if (resultado[position].dificulty == "D")
                            time.pontuacao += gincana.pontos!![3]
                    }
                }
                holder.view.txtQuestionNumberResultGincana.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_up_black_24dp, 0)
                timesbtns[0].text = resultado[position].selectedAnswer
                timesbtns[0].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(gincana.context!!, R.color.correctAnswer))
                timesbtns[1].visibility = View.GONE
            }
        }


        holder.view.hiddenResultGincana.visibility = View.GONE
        holder.view.setOnClickListener {
            if (holder.view.hiddenResultGincana.visibility == View.GONE) {
                holder.view.hiddenResultGincana.visibility = View.VISIBLE
            } else {
                holder.view.hiddenResultGincana.visibility = View.GONE
                notifyItemChanged(position)
            }
        }

        holder.view.setOnLongClickListener {

            val alertDialog = AlertDialog.Builder(gincana.context!!).create()
            alertDialog.setTitle("Confirmar")
            alertDialog.setMessage("Você deseja remover o item?")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, which ->
                resultado.removeAt(position)
                notifyItemRemoved(position)
                notifyItemChanged(position, resultado.size)
                gincana.updateView(resultado.size)
            }
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Fechar") { dialog, which -> dialog.dismiss() }
            alertDialog.show()


            return@setOnLongClickListener true

        }
    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    init {
        /*view.setOnClickListener {
            if(view.hiddenResult.visibility == View.GONE){
                view.hiddenResult.visibility = View.VISIBLE
            }
            else {
                view.hiddenResult.visibility = View.GONE
            }
        }*/
    }
}