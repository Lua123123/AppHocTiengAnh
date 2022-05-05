package com.example.englanguage.viewmodel

import android.R
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englanguage.MainActivity
import com.example.englanguage.koin.KoinRepository
import com.example.englanguage.model.login.Login
import com.example.englanguage.model.login.UserLogin
import com.example.englanguage.model.vocabulary.SuccessVocabulary
import com.example.englanguage.model.vocabulary.Vocabulary
import com.example.englanguage.network.API
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlipViewModel(val koinRepository: KoinRepository) : ViewModel() {

    val mVocabulary = MutableLiveData<Vocabulary>()
    val mEmail = MutableLiveData<Login>()

    private var vocabulary: Vocabulary? = null
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun koinGetVocabulary() = koinRepository.getVocabulary()

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun mutableLiveDataClickGetVocabulary() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            clickGetVocabulary()
        }
    }

    fun clickGetVocabulary(): List<SuccessVocabulary?>? {

        API.api.getVocabulary(1, "", "10").enqueue(object : Callback<Vocabulary?> {
            override fun onResponse(call: Call<Vocabulary?>, response: Response<Vocabulary?>) {
                var vocabulary: Vocabulary? = response.body()
                if (vocabulary != null) {
                    mVocabulary.postValue(vocabulary!!)
                } else {
                    onError("Error: ${response.message()} ")
                }
            }

            override fun onFailure(call: Call<Vocabulary?>, t: Throwable) {
            }
        })
        return null
    }

//    fun clickLogin() {
//        var email: String = "hvna2802@gmail.com"
//        var password: String = "12345678"
//        val userLogin = UserLogin(email, password, true)
//        Log.d("userLogin", userLogin.toString())
//        API.api.getUsers(userLogin).enqueue(object : Callback<Login?> {
//            override fun onResponse(call: Call<Login?>, response: Response<Login?>) {
//                val login = response.body()
//                Log.d("login", login.toString())
//                Log.d("login", login!!.message)
//                Log.d("login", login.status.toString())
//                Log.d("login", login.data.token_type)
//                Log.d("login", login.data.access_token)
//                mEmail.postValue(login!!)
//            }
//
//            override fun onFailure(call: Call<Login?>, t: Throwable) {
//            }
//        })
//    }

}