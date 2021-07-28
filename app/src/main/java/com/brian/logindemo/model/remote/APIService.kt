package com.brian.logindemo.model.remote

import com.brian.logindemo.model.data.UserData
import com.google.gson.JsonObject
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*


const val BASE_URL = "https://watch-master-staging.herokuapp.com/"

interface APIService {

    @Headers("X-Parse-Application-Id: vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD")
    @GET("api/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Single<Response<UserData>>

    @PUT("api/users/{objectId}")
    fun updateUser(
        @HeaderMap headers: Map<String, String>,
        @Path("objectId") objectId: String,
        @Query("timezone") timezone: Int
    ): Single<Response<UserData>>
}