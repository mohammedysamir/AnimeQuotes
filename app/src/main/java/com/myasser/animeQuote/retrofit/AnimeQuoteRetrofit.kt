package com.myasser.animeQuote.retrofit

import com.myasser.animeQuote.models.AnimeQuote
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeQuoteRetrofit {
    @GET("quotes/character")
    fun getQuoteByCharacter(@Query("name") characterName: String): Call<ArrayList<AnimeQuote>>

    @GET("quotes/anime")
    fun getQuoteByAnimeTitle(@Query("title") animeTitle: String): Call<ArrayList<AnimeQuote>>

    @GET("random")
    fun getRandomQuote(): Call<AnimeQuote>
}