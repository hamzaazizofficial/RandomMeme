package com.example.memeshare

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.memeshare.data.ApiService
import kotlinx.coroutines.launch


class MemeViewModel(application: Application) : AndroidViewModel(application) {

    private val _memeUrl: MutableLiveData<String> = MutableLiveData()
    val memeUrl: LiveData<String> = _memeUrl
    private val apiService = ApiService.create()

    fun loadMeme(subredditName: String) {
        viewModelScope.launch {
            val memeModel = apiService.getMemeUrl(subredditName)
            _memeUrl.value = memeModel.memeUrl
        }
    }
}
