package com.brian.logindemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brian.logindemo.databinding.ActivityMainBinding
import com.brian.logindemo.model.remote.APIService
import com.brian.logindemo.model.remote.BASE_URL
import com.brian.logindemo.viewmodel.MainViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val remote = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIService::class.java)

        viewModel = MainViewModel(remote)

        viewModel.userData.observe(this, {
            val intent = Intent(this, ResultAcitvity::class.java).apply {
                putExtra("userdata", it)
            }
            startActivity(intent)
        })

        binding.button.setOnClickListener {
//            val anotherName = "Test Success"
//            viewModel.name.value = anotherName
            viewModel.login(binding.editUsername.text.toString(), binding.editPassword.text.toString())
        }
    }
}