package com.bsoftwares.benjamin.ideia01.gamemodes.quizgame

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.R
import com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame.Time
import kotlinx.android.synthetic.main.item_jogador.view.*
import java.util.*

class RankingAdapter(val jogadores : ArrayList<Time>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_jogador, parent, false)
        return CustomViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        return jogadores.count()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.txtNomeJogador.text = jogadores[position].nome
        holder.view.txtPontuacaoJogador.text = jogadores[position].pontuacao.toString()
    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    init {

    }
}