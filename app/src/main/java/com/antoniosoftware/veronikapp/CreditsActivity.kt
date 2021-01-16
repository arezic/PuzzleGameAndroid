package com.antoniosoftware.veronikapp

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_credits.*

class CreditsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        val textViewCredits1 = textView_credits1
        textViewCredits1.movementMethod = LinkMovementMethod.getInstance()
    }
}