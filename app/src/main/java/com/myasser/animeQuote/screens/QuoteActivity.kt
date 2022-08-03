package com.myasser.animeQuote.screens

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import animeQuote.R


class QuoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_quote)
        findViewById<TextView>(R.id.animeName).text = intent.getStringExtra("Anime Name")
        findViewById<TextView>(R.id.characterName).text = "— ${intent.getStringExtra("Character Name")}"
        findViewById<TextView>(R.id.animeQuote).text = "❝ ${intent.getStringExtra("Quote")} ❞"
    }
}