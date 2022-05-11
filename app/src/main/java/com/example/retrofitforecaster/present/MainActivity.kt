package com.example.retrofitforecaster.present

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitforecaster.databinding.ActivityMainBinding
import com.example.retrofitforecaster.model.WeatherViewModel

class MainActivity : AppCompatActivity() {
    lateinit var layoutManager: LinearLayoutManager
    private val adapter by lazy { WeatherAdapter() }
    private val model: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        observe()
        buttonListener()
    }


    private fun addItemDecoration(rv: RecyclerView): DividerItemDecoration {
        return DividerItemDecoration(
            rv.context,
            LinearLayoutManager.VERTICAL
        )
    }

    private fun buttonListener() {
        binding.searchButton.setOnClickListener {
            search()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        binding.rView.layoutManager = layoutManager
        binding.rView.adapter = adapter
        binding.rView.addItemDecoration(addItemDecoration(binding.rView))
    }

    private fun observe() {
        model.weatherList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun search() {
        model.getWeather(binding.searchEditText.text.toString())
        adapter.submitList(model.weatherList.value)
        adapter.notifyDataSetChanged()
    }
}
