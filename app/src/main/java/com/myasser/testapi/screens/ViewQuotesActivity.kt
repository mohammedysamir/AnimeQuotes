package com.myasser.testapi.screens

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myasser.testapi.AnimeQuoteAdapter
import com.myasser.testapi.R
import com.myasser.testapi.models.AnimeQuote

//receive list of anime quotes and assign it to recycler view
/**
 * Show list of quotes with character/anime name chosen by user
 * @param quotesList: represent quotes to be fetched from API
 * @param name: will be displayed as character/anime name at the top of the activity
 */
class ViewQuotesActivity(private val quotesList: ArrayList<AnimeQuote>, private val name: String) :
    AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quotes)
        recyclerView = findViewById(R.id.quotesRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = AnimeQuoteAdapter(quotes = quotesList)
        findViewById<TextView>(R.id.nameText).text = name
    }
}