package com.bsoftwares.benjamin.ideia01.gamemodes.quizgame

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.resultquizlayout.view.*

class ResultsAdapter(val resultado : ArrayList<QuestionParcelable>, val context: Context) : RecyclerView.Adapter<CustomViewHolder>(){

    var mExpandedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.resultquizlayout,parent,false)
        return CustomViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        return resultado.count()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.txtQuestionNumberResult.text = context.getString(R.string.pergunta,position+1)
        holder.view.txtQuestionResult.text = resultado[position].question
        holder.view.txtQuestionResult.compoundDrawablePadding = 20
        holder.view.btnRespostaSelecionada.text = resultado[position].selectedAnswer
        holder.view.btnRespostaCorreta.text = resultado[position].correctAnswer
        holder.view.txtReferencia.text = context.getString(R.string.referencia,resultado[position].reference)
        holder.view.hiddenResult.visibility = View.GONE

        if(resultado[position].reference=="")
            holder.view.txtReferencia.visibility = View.GONE

        holder.view.setOnClickListener {
            if(holder.view.hiddenResult.visibility == View.GONE){
                holder.view.hiddenResult.visibility = View.VISIBLE
            }
            else {
                holder.view.hiddenResult.visibility = View.GONE
                notifyItemChanged(position)
            }
        }

        if(holder.view.btnRespostaSelecionada.text == holder.view.btnRespostaCorreta.text){
            holder.view.btnRespostaCorreta.visibility = View.GONE
            holder.view.btnRespostaSelecionada.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.correctAnswer))
            holder.view.txtQuestionNumberResult.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_thumb_up_black_24dp,0)
        } else{
            holder.view.btnRespostaSelecionada.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.wrongAnswer))
            holder.view.btnRespostaCorreta.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.correctAnswer))
            holder.view.txtQuestionNumberResult.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_thumb_down_black_24dp,0)
        }
    }

}
