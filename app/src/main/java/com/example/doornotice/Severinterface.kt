package com.example.doornotice

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Severinterface {
    @POST("/user/1")
    fun updateUser(@Body user: User): Call<User>
}