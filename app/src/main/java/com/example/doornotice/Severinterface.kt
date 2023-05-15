package com.example.doornotice

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Severinterface {
    @POST("/user/{id}")
    fun updateUser(@Path("id") id: String, @Query("state") state: String): Call<User>
}