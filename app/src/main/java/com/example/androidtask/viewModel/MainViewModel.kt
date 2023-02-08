package com.example.androidtask.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtask.model.Data
import com.example.androidtask.model.repository.GetPersonDetailsRepository

class MainViewModel(context: Context) : ViewModel() {

    private val getPersonRespository = GetPersonDetailsRepository()
    fun getPersonDetails() {
        getPersonRespository.getPersonDetails()
    }

    fun getPersonDetailResult(): MutableLiveData<List<Data>> {
        return getPersonRespository.getPersonList
    }

    fun geterror(): MutableLiveData<String> {
        return getPersonRespository.errorMessage
    }
}