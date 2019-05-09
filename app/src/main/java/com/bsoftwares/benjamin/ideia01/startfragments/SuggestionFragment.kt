package com.bsoftwares.benjamin.ideia01.startfragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast

import com.bsoftwares.benjamin.ideia01.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_gincana_scores.*
import kotlinx.android.synthetic.main.fragment_suggestion.*
import kotlinx.android.synthetic.main.pergunta_gincana.*


class SuggestionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_suggestion, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mFirebase = FirebaseFirestore.getInstance().document("suggestion/questions")

        btn_enviar.setOnClickListener {

            if (ed_pergunta.text.isEmpty()||ed_correta.text.isEmpty()||ed_errada1.text.isEmpty()||ed_errada2.text.isEmpty()||ed_errada3.text.isEmpty()||ed_ref_biblica.text.isEmpty()||rg_dificuldade.checkedRadioButtonId==-1){
                val um = ed_pergunta.text.isEmpty()
                val dois = ed_correta.text.isEmpty()
                val tres = ed_errada1.text.isEmpty()
                val quatro = ed_errada2.text.isEmpty()
                val cinco = ed_errada3.text.isEmpty()
                val seis = ed_ref_biblica.text.isEmpty()
                val sete = (rg_dificuldade.checkedRadioButtonId == -1)
                Toast.makeText(context,"Verifique se todos os campos foram preenchidos",Toast.LENGTH_LONG).show()

            } else{

                val suggestionMap = HashMap<String, String>()

                suggestionMap.put("question",ed_pergunta.text.toString())
                suggestionMap.put("correctAnswer",ed_correta.text.toString())
                suggestionMap.put("answerB",ed_errada1.text.toString())
                suggestionMap.put("answerC",ed_errada2.text.toString())
                suggestionMap.put("answerD",ed_errada3.text.toString())
                val idRbSelecionadoDificuldade =
                    rg_dificuldade.findViewById<RadioButton>(rg_dificuldade.checkedRadioButtonId)
                val posicaoRbSelecionadoDificuldade =
                    rg_dificuldade.indexOfChild(idRbSelecionadoDificuldade)
                val rbSelecionadoDificuldade =
                    rg_dificuldade.getChildAt(posicaoRbSelecionadoDificuldade) as RadioButton
                suggestionMap.put("dificulty",rbSelecionadoDificuldade.text.toString())
                suggestionMap.put("reference",ed_ref_biblica.text.toString())

                mFirebase.collection("Perguntas").add(suggestionMap as Map<String, Any>)
                    .addOnSuccessListener {
                        Toast.makeText(context,"Pergunta Enviada",Toast.LENGTH_SHORT).show()
                        ed_correta.text.clear()
                        ed_errada1.text.clear()
                        ed_errada2.text.clear()
                        ed_errada3.text.clear()
                        ed_pergunta.text.clear()
                        ed_ref_biblica.text.clear()
                        rg_dificuldade.clearCheck()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"Falhou, cheque sua conexão e tente mais tarde",Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }


}
