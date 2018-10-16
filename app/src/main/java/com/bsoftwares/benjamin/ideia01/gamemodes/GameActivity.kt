package com.bsoftwares.benjamin.ideia01.gamemodes

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bsoftwares.benjamin.ideia01.gamemodes.gincanagame.GincanaTeams
import com.bsoftwares.benjamin.ideia01.gamemodes.quizgame.GameFragment
import com.bsoftwares.benjamin.ideia01.questions.QuestionParcelable
import com.bsoftwares.benjamin.ideia01.R

class GameActivity : AppCompatActivity() {

    var gameFragment : Fragment = GameFragment()
    var gincanaTeams : Fragment = GincanaTeams()
    private var doubleBackToExitPressedOnce = false
    val bundle : Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        bundle.putParcelableArrayList("PerguntasSelecionadas",intent.extras!!.getParcelableArrayList<QuestionParcelable>("PerguntasSelecionadas"))
        if (intent.extras!!.containsKey("isGincana")){
            gincanaTeams.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameGame, gincanaTeams).commit()
        }else{
            if (intent.extras!!.containsKey("isGameShow"))
                bundle.putBoolean("isGameShow",intent.extras!!.getBoolean("isGameShow"))
            else
                bundle.putBoolean("tempoParaPergunta",intent.extras!!.getBoolean("tempoParaPergunta"))
            var fragment = supportFragmentManager.findFragmentByTag("Game Fragment")
            if (fragment==null)
                fragment = gameFragment
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameGame, fragment,"Game Fragment").commit()
        }

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            this.finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Clique de novo para sair da partida", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
