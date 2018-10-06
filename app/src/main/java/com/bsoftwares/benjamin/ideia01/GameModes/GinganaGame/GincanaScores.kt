package com.bsoftwares.benjamin.ideia01.GameModes.GinganaGame


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bsoftwares.benjamin.ideia01.R

class GincanaScores : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gincana_scores, container, false)
    }

    var todosRespondem = false
    var times : java.util.ArrayList<Time>? = java.util.ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var points = arguments!!.getIntegerArrayList("Pontos")
        todosRespondem = arguments!!.getBoolean("umOuTodos")
        times = arguments!!.getParcelableArrayList("Times")
    }

}
