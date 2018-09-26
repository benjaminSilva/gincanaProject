package com.bsoftwares.benjamin.ideia01.GameModes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.bsoftwares.benjamin.ideia01.GameModes.QuizGame.GameFragment
import com.bsoftwares.benjamin.ideia01.Questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R

class GameActivity : AppCompatActivity() {

    val gameFragment : Fragment = GameFragment()
    val bundle : Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        bundle.putParcelableArrayList("PerguntasSelecionadas",intent.extras!!.getParcelableArrayList<QuestionParcelable>("PerguntasSelecionadas"))
        if (intent.extras!!.containsKey("isGameShow")){
            bundle.putBoolean("isGameShow",intent.extras!!.getBoolean("isGameShow"))
        }else{
            bundle.putBoolean("tempoParaPergunta",intent.extras!!.getBoolean("tempoParaPergunta"))
        }
        gameFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameGame, gameFragment,"gameOn").commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}
