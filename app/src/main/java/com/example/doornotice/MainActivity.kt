package com.example.doornotice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.doornotice.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofitevent("1","51")

    }

    private fun retrofitevent(id :String,state:String){
         val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
                override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
                    try{ nextResponseBodyConverter.convert(value) }
                    catch (e:Exception){ e.printStackTrace()
                        null }
                } else{ null }
            }
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://421e-210-119-32-156.ngrok-free.app/")
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val objectretrofit = retrofit.create(Severinterface::class.java)

        objectretrofit.updateUser(id, state).also {
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