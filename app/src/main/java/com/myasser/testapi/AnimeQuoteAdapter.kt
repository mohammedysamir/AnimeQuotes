package com.myasser.testapi

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myasser.testapi.models.AnimeQuote
import com.myasser.testapi.screens.QuoteActivity

class AnimeQuoteAdapter(var quotes: ArrayList<AnimeQuote>) :
    RecyclerView.Adapter<AnimeQuoteAdapter.ViewHolder>() {
    class ViewHolder(quoteView: View) : RecyclerView.ViewHolder(quoteView) {
        var mainText: TextView = quoteView.findViewById(R.id.mainText)
        var secondaryText: TextView = quoteView.findViewById(R.id.secondaryText)
        var quoteText: TextView = quoteView.findViewById(R.id.quoteText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.quote_holder, parent, false)
        )
        vh.itemView.setOnClickListener {
            //todo: test intent constructor
            val intent = Intent(parent.context, QuoteActivity::class.java).apply {
                putExtra("Anime Name", vh.mainText.text)
                putExtra("Character Name", vh.secondaryText.text)
                putExtra("Quote", vh.quoteText.text)
            }
            parent.context.startActivity(intent)
        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quoteItem = quotes[position]
        holder.mainText.text = quoteItem.animeName
        holder.secondaryText.text = quoteItem.animeCharacter
        holder.quoteText.text = quoteItem.quote
    }

    override fun getItemCount() = quotes.size
}