package com.example.androidtask.model.netWork

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObj {
     val retrofit = Retrofit.Builder()
         .baseUrl("https://reqres.in/api/")
         .addConverterFactory(GsonConverterFactory.create())
         .build()

}