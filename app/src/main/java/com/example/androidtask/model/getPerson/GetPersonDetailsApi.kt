package com.example.androidtask.model.getPerson

import com.example.androidtask.model.PersonDetailsModel
import retrofit2.Call
import retrofit2.http.GET

interface GetPersonDetailsApi {
@GET("users?page=2")
fun getPersonDetailsAPI():Call<PersonDetailsModel>
}