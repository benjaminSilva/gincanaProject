package com.bsoftwares.benjamin.ideia01.QuizGame

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R
import com.bsoftwares.benjamin.ideia01.StartFragment

class GameActivity : AppCompatActivity() {

    var lista : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    var listaSelecionadas : java.util.ArrayList<QuestionParcelable>? = java.util.ArrayList()
    val gameFragment : Fragment = GameFragment()
    val bundle : Bundle = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        lista = intent.extras.getParcelableArrayList<QuestionParcelable>("Perguntas")
        listaSelecionadas = intent.extras.getParcelableArrayList<QuestionParcelable>("PerguntasSelecionadas")
        //Toast.makeText(this,""+ listaDePerguntas!![0].answerA,Toast.LENGTH_SHORT).show()
        bundle.putParcelableArrayList("Perguntas",lista)
        bundle.putParcelableArrayList("PerguntasSelecionadas",listaSelecionadas)
        gameFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameGame, gameFragment).commit()
    }


}
