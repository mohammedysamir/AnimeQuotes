package com.myasser.testapi.retrofit

import com.myasser.testapi.models.AnimeQuote
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
//todo: try to fetch quote by anime title and character name