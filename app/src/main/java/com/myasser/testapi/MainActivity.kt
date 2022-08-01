package com.myasser.testapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.myasser.testapi.models.AnimeQuote
import com.myasser.testapi.retrofit.AnimeQuoteRetrofit
import com.myasser.testapi.screens.QuoteActivity
import com.myasser.testapi.screens.ViewQuotesActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var retrofitBuilder: Retrofit
    private lateinit var animeInterface: AnimeQuoteRetrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //todo: define retrofit builder, fetch quote and send them to other activities
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
                val animeTextView = findViewById<TextView>(R.id.byAnimeNameTextField)
                val characterTextView = findViewById<TextView>(R.id.byCharacterNameTextField)
                if (animeTextView.text.isEmpty()) {
                    val characterName = characterTextView.text.toString().trim()
                    fetchQuoteByName(characterName, false)
//                    //search by character
//                    val call: Call<ArrayList<AnimeQuote>> =
//                        animeInterface.getQuoteByCharacter(characterName = characterName)
//                    call.enqueue(object : Callback<ArrayList<AnimeQuote>> {
//                        override fun onResponse(
//                            call: Call<ArrayList<AnimeQuote>>?,
//                            response: Response<ArrayList<AnimeQuote>>?
//                        ) {
//                            startActivity(
//                                Intent(
//                                    v.context,
//                                    ViewQuotesActivity(
//                                        response?.body()!!,
//                                        characterName
//                                    )::class.java
//                                )
//                            )
//                        }
//
//                        override fun onFailure(call: Call<ArrayList<AnimeQuote>>?, t: Throwable?) {
//                            Toast.makeText(
//                                v.context,
//                                "Couldn't find quotes for selected character",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                    })
                } else if (animeTextView.text.isNotEmpty()) {
                    //search by anime name
                    val animeTitle = animeTextView.text.toString().trim()
                    fetchQuoteByName(animeTitle, true)
                    //search by character
//                    val call: Call<ArrayList<AnimeQuote>> =
//                        animeInterface.getQuoteByAnimeTitle(animeTitle = animeTitle)
//                    call.enqueue(object : Callback<ArrayList<AnimeQuote>> {
//                        override fun onResponse(
//                            call: Call<ArrayList<AnimeQuote>>?,
//                            response: Response<ArrayList<AnimeQuote>>?
//                        ) {
//                            startActivity(
//                                Intent(
//                                    v.context,
//                                    ViewQuotesActivity(
//                                        response?.body()!!,
//                                        animeTitle
//                                    )::class.java
//                                )
//                            )
//                        }
//
//                        override fun onFailure(call: Call<ArrayList<AnimeQuote>>?, t: Throwable?) {
//                            Toast.makeText(
//                                v.context,
//                                "Couldn't find quotes for selected anime title",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                    })
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
                            putExtra("Anime Name", response?.body()?.animeName)
                            putExtra("Character Name", response?.body()?.animeCharacter)
                            putExtra("Quote", response?.body()?.quote)
                        }
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<AnimeQuote>?, t: Throwable?) {
                        Toast.makeText(v.context, "Sorry, something went wrong!", Toast.LENGTH_LONG)
                            .show()
                    }

                })
            }
        }
    }

    private fun fetchQuoteByName(name: String, isAnimeTitle: Boolean) {
        if (isAnimeTitle) {
            val call: Call<ArrayList<AnimeQuote>> =
                animeInterface.getQuoteByAnimeTitle(animeTitle = name)
            return call.enqueue(object : Callback<ArrayList<AnimeQuote>> {
                override fun onResponse(
                    call: Call<ArrayList<AnimeQuote>>?,
                    response: Response<ArrayList<AnimeQuote>>?
                ) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            ViewQuotesActivity(
                                response?.body()!!,
                                name
                            )::class.java
                        )
                    )
                }

                override fun onFailure(call: Call<ArrayList<AnimeQuote>>?, t: Throwable?) {
                    Toast.makeText(
                        this@MainActivity,
                        "Couldn't find quotes for selected anime title",
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
                    startActivity(
                        Intent(
                            this@MainActivity,
                            ViewQuotesActivity(
                                response?.body()!!,
                                name
                            )::class.java
                        )
                    )
                }

                override fun onFailure(call: Call<ArrayList<AnimeQuote>>?, t: Throwable?) {
                    Toast.makeText(
                        this@MainActivity,
                        "Couldn't find quotes for selected character",
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