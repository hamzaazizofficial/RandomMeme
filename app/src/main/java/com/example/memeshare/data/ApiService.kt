package com.example.memeshare.data

import com.example.memeshare.model.MemeModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("gimme/{subredditName}")
    suspend fun getMemeUrl(@Path("subredditName") subredditName: String): MemeModel

    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://meme-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}