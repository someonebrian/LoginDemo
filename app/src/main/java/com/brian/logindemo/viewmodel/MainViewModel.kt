package com.brian.logindemo.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brian.logindemo.model.data.UserData
import com.brian.logindemo.model.remote.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class MainViewModel(private val remote: APIService) : ViewModel() {

    val userData = MutableLiveData<UserData>()

    @SuppressLint("CheckResult")
    fun login(username: String, password: String) {

        remote.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: Response<UserData>? ->
                val code = response?.code()

                userData.value = response?.body()
            }, {throwable: Throwable? ->
                Log.d("[login][error]", throwable?.message ?: "error")
            })
    }
}