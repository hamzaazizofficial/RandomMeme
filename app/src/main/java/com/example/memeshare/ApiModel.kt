package com.example.memeshare

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class ApiModel(application: Application) : AndroidViewModel(application) {
    //    lateinit var currentImgUrl: String
    fun loadMeme(onSuccess: (JSONObject) -> Unit) {
        val queue = Volley.newRequestQueue(getApplication())
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                onSuccess(response)
                Log.d("checkkk", url)
            },
            {

            })
        //Add the request to the request que
        queue.add(jsonObjectRequest)
    }
}