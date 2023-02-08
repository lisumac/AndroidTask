package com.example.androidtask.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.androidtask.model.Data
import com.example.androidtask.model.PersonDetailsModel
import com.example.androidtask.model.getPerson.GetPersonDetailsApi
import com.example.androidtask.model.netWork.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetPersonDetailsRepository {
    val getPersonList = MutableLiveData<List<Data>>()
    val errorMessage = MutableLiveData<String>()
    fun getPersonDetails() {
        val getPerson = RetrofitObj.retrofit.create(GetPersonDetailsApi::class.java)
        getPerson.getPersonDetailsAPI().enqueue(object : Callback<PersonDetailsModel> {
            override fun onResponse(
                call: Call<PersonDetailsModel>,
                response: Response<PersonDetailsModel>
            ) {
                if (response.isSuccessful) {
                    getPersonList.postValue(response.body()?.data)
                } else {
                    errorMessage.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<PersonDetailsModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }


}