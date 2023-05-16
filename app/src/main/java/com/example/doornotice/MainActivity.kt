package com.example.doornotice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.doornotice.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var snackbar : Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        snackbar = Snackbar.make(binding.root, "현재 상태를 변경하였습니다.", Snackbar.LENGTH_LONG)
        Button()
    }

    private fun Button() {
        binding.IndoorButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 재실로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "1")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.MeetingButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 회의로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "2")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.LectureButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 수업으로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "3")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.EatButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 식사로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "4")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.BusinessTripButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 출장으로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "5")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.OutingButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 외출로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "6")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.ConsultingButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 학생 상담으로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "7")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.GyonaeButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 교내 외출로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "8")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }

        binding.LeaveWorkButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("상태 확인")
            builder.setMessage("현재 상태를 퇴근으로 변경하시겠습니까?")
            builder.setPositiveButton("네") { dialog, which ->
                retrofitevent("1", "9")
                snackbar.show()
            }
            builder.setNegativeButton("아니요") { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }
    }

    private fun retrofitevent(id :String, state:String){
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