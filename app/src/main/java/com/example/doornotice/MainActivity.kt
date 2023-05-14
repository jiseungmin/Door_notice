package com.example.doornotice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.doornotice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val objectretrofit = retrofit.create(Severinterface::class.java)
        val Userinfo = User("1","1","지승민","012312312")

        objectretrofit.updateUser(Userinfo).also {
            it.enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val updatedUser = response.body()
                        Log.d("ServerResponse", "User updated: $updatedUser")
                    } else {
                        Log.e("ServerResponse", "Failed to update user: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("ServerResponse", "Failed to update user: ${t.message}")
                }
            })
        }


    }
}