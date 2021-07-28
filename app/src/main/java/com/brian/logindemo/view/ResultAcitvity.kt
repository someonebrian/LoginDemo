package com.brian.logindemo.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brian.logindemo.databinding.ActivityResultBinding
import com.brian.logindemo.model.data.UserData
import com.brian.logindemo.model.remote.APIService
import com.brian.logindemo.model.remote.BASE_URL
import com.brian.logindemo.viewmodel.ResultViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class ResultAcitvity: AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userData = intent.getSerializableExtra("userdata") as UserData

        binding.textEmailResult.text = userData.email

        val remote = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIService::class.java)

        viewModel = ResultViewModel(remote)

        val options = TimeZone.getAvailableIDs()
        val list = ArrayList<String>()
        for (i in options.indices) {
            list.add(options[i])
        }
        spinner = binding.spinner
        spinner.also {
            it.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val tz = TimeZone.getTimeZone(list[position])
                    val offset = tz.rawOffset/ 3600 / 1000

                    Log.d("[timezone]", offset.toString())

                    viewModel.update(userData.token, userData.id, offset)
                }
            }
        }

        viewModel.update.observe(this,{
            Toast.makeText(this, "Update success at $it",Toast.LENGTH_LONG).show()
        })
    }
}