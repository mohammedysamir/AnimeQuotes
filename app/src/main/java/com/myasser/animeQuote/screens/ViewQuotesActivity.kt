package com.myasser.animeQuote.screens

import animeQuote.R
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myasser.animeQuote.AnimeQuoteAdapter
import com.myasser.animeQuote.MainActivity

class ViewQuotesActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quotes)
        recyclerView = findViewById(R.id.quotesRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val quotes = MainActivity.incomingResponse
        recyclerView.adapter = AnimeQuoteAdapter(quotes = quotes)
        findViewById<TextView>(R.id.nameText).text = intent.getStringExtra("Anime Name")
    }
}