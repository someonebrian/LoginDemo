package com.brian.logindemo.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserData(
    @SerializedName("objectId")
    val id: String = "",

    @SerializedName("username")
    val username: String = "",

    @SerializedName("reportEmail")
    val email: String = "",

    @SerializedName("timezone")
    val timezone: Int = 0,

    @SerializedName("sessionToken")
    val token: String = "",

    @SerializedName("updatedAt")
    val updateAt: String = ""
): Serializable