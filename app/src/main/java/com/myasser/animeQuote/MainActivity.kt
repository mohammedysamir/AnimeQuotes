package com.myasser.animeQuote

import animeQuote.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.myasser.animeQuote.models.AnimeQuote
import com.myasser.animeQuote.retrofit.AnimeQuoteRetrofit
import com.myasser.animeQuote.screens.QuoteActivity
import com.myasser.animeQuote.screens.ViewQuotesActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var retrofitBuilder: Retrofit
    private lateinit var animeInterface: AnimeQuoteRetrofit

    companion object {
        lateinit var incomingResponse: ArrayList<AnimeQuote>
    }

    init {
        incomingResponse = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofitBuilder = Retrofit.Builder().baseUrl("https://animechan.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        animeInterface = retrofitBuilder.create(AnimeQuoteRetrofit::class.java)

        //set button listeners
        findViewById<AppCompatButton>(R.id.searchButton).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.searchRandomButton).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.searchButton -> {
                val animeTextView = findViewById<TextInputEditText>(R.id.byAnimeNameTextField)
                val characterTextView = findViewById<TextInputEditText>(R.id.byCharacterNameTextField)
                if (animeTextView.text?.isEmpty()!!) {
                    val characterName = characterTextView.text.toString().trim()
                    fetchQuoteByName(characterName, false)
                } else if (animeTextView.text?.isNotEmpty()!!) {
                    //search by anime name
                    val animeTitle = animeTextView.text.toString().trim()
                    fetchQuoteByName(animeTitle, true)
                } else if (animeTextView.text?.isNotEmpty()!! && characterTextView.text?.isNotEmpty()!!) {
                    Toast.makeText(
                        v.context,
                        getString(R.string.query_both_errors),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    //button is clicked without query
                    animeTextView.error = getString(R.string.empty_query)
                    characterTextView.error = getString(R.string.empty_query)
                }
            }
            R.id.searchRandomButton -> {
                val call: Call<AnimeQuote> = animeInterface.getRandomQuote()
                call.enqueue(object : Callback<AnimeQuote> {
                    override fun onResponse(
                        call: Call<AnimeQuote>?,
                        response: Response<AnimeQuote>?
                    ) {
                        val intent = Intent(v.context, QuoteActivity::class.java).apply {
                            putExtra("Anime Name", response?.body()?.anime)
                            putExtra("Character Name", response?.body()?.character)
                            putExtra("Quote", response?.body()?.quote)
                        }
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<AnimeQuote>?, t: Throwable?) {
                        Toast.makeText(
                            v.context,
                            getString(R.string.fetch_error),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                })
            }
        }
    }

    private fun fetchQuoteByName(name: String, isAnimeTitle: Boolean) {
        val viewQuoteIntent = Intent(this@MainActivity, ViewQuotesActivity::class.java).apply {
            putExtra("Anime Name", name)
        }
        if (isAnimeTitle) {
            val call: Call<ArrayList<AnimeQuote>> =
                animeInterface.getQuoteByAnimeTitle(animeTitle = name)
            call.enqueue(object : Callback<ArrayList<AnimeQuote>> {
                override fun onResponse(
                    call: Call<ArrayList<AnimeQuote>>?,
                    response: Response<ArrayList<AnimeQuote>>?
                ) {
                    if (response?.isSuccessful!!) {
                        incomingResponse = response.body()!!
                        if (incomingResponse.size > 0)
                            startActivity(viewQuoteIntent)
                        else
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.anime_not_found_error),
                                Toast.LENGTH_LONG
                            ).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<AnimeQuote>>?, t: Throwable?) {
                    Log.i("Error", t.toString())
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.anime_not_found_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        } else {
            val call: Call<ArrayList<AnimeQuote>> =
                animeInterface.getQuoteByCharacter(characterName = name)
            call.enqueue(object : Callback<ArrayList<AnimeQuote>> {
                override fun onResponse(
                    call: Call<ArrayList<AnimeQuote>>?,
                    response: Response<ArrayList<AnimeQuote>>?
                ) {
                    if (response?.isSuccessful!!) {
                        incomingResponse = response.body()!!
                        if (incomingResponse.size > 0)
                            startActivity(viewQuoteIntent)
                        else
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.character_not_found_error),
                                Toast.LENGTH_LONG
                            ).show()

                    }
                }

                override fun onFailure(call: Call<ArrayList<AnimeQuote>>?, t: Throwable?) {
                    Log.i("Error", t.toString())
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.character_not_found_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }

    }
}
/*
* 1. Create model class that will be populated with response
* 2. User has 3 options to get a quote -> by character name, by anime name, randomly
* 3. Quotes will be displayed in a recycler view if its by character or anime name
* 4. Create button for Random quote and another for search that will utilize anime/character name.
* */