package com.bsoftwares.benjamin.ideia01.startfragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bsoftwares.benjamin.ideia01.R
import kotlinx.android.synthetic.main.fragment_config.*

class ConfigFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
    }

    private fun setButtons() {
        btn_config_send.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.frameMain, SuggestionFragment())
                .addToBackStack("Enviar").commit()
        }
        btn_privacy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://progdaora.blogspot.com/2019/04/privacy-policy-of-gincana-mobile.html")
                )
            )
        }

        btn_rate_us.setOnClickListener {
            startActivity(
                Intent(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.bsoftwares.benjamin.ideia01")
                    )
                )
            )
        }

    }

}
