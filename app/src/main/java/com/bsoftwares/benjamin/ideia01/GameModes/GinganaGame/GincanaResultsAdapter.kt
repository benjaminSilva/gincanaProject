package com.bsoftwares.benjamin.ideia01.GameModes.GinganaGame

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.resultgincanalayout.view.*
import java.util.*

class GincanaResultsAdapter(val resultado : ArrayList<QuestionParcelable>, val context: Context, val timesJogo : java.util.ArrayList<Time>?, val todosRespondem : Boolean) : RecyclerView.Adapter<CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.resultgincanalayout,parent,false)
        return CustomViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        return resultado.count()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.txtQuestionNumberResultGincana.text = context.getString(R.string.pergunta,position+1)
        holder.view.txtPerguntaGincana.text = resultado[position].question
        val timesbtns = Arrays.asList(holder.view.timeResultsGincana01,holder.view.timeResultsGincana02,holder.view.timeResultsGincana03,holder.view.timeResultsGincana04)

        if (todosRespondem){
            holder.view.txtDificuldadeResults.text = resultado[position].dificulty
            for (i in 0.. timesJogo!!.size-1){
                timesbtns[i].text = timesJogo[i].nome
                timesbtns[i].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.wrongAnswer))
                for (j in 0..resultado[position].timesAcertaram.size-1){
                    if(resultado[position].timesAcertaram[j]==timesJogo[i].nome){
                        timesbtns[i].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.correctAnswer))
                    }
                }
                timesbtns[i].visibility = View.VISIBLE
            }
        } else{
            holder.view.txtDificuldadeResults.text = resultado[0].timesAcertaram[0] + " " +  resultado[position].dificulty
            if (resultado[position].selectedAnswer!=resultado[position].correctAnswer){
                holder.view.txtQuestionNumberResultGincana.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_thumb_down_black_24dp,0)
                timesbtns[0].text = resultado[position].selectedAnswer
                timesbtns[0].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.wrongAnswer))
                timesbtns[1].text = resultado[position].correctAnswer
                timesbtns[1].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.correctAnswer))
            }else{
                holder.view.txtQuestionNumberResultGincana.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_thumb_up_black_24dp,0)
                timesbtns[0].text = resultado[position].selectedAnswer
                timesbtns[0].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.correctAnswer))
                timesbtns[1].visibility = View.GONE
            }
        }


        holder.view.hiddenResultGincana.visibility = View.GONE
        holder.view.setOnClickListener {
            if(holder.view.hiddenResultGincana.visibility == View.GONE){
                holder.view.hiddenResultGincana.visibility = View.VISIBLE
            }
            else {
                holder.view.hiddenResultGincana.visibility = View.GONE
                notifyItemChanged(position)
            }
        }
    }

}

class CustomViewHolder(val view : View): RecyclerView.ViewHolder(view){
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