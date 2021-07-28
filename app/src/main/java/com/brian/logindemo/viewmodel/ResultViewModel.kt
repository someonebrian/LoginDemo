package com.brian.logindemo.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brian.logindemo.model.data.UserData
import com.brian.logindemo.model.remote.APIService
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*

class ResultViewModel(private val remote: APIService): ViewModel() {

    val update = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun update(token: String, id: String,timeZone: Int) {

        remote.updateUser(getHeaderMap(token), id, timeZone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: Response<UserData>? ->
                val code = response?.code()
                Log.d("[ResultViewModel]", response.toString())

                update.value = response?.body()?.updateAt.toString()
            }, {throwable: Throwable? ->
                Log.d("[update][error]", throwable?.message ?: "error")
            })
    }

    private fun getHeaderMap(token: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["X-Parse-Application-Id"] = "vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD"
        headerMap["X-Parse-Session-Token"] = token
        return headerMap
    }
}