package com.brian.logindemo.model

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure()
}