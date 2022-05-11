package com.example.retrofitforecaster.present

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitforecaster.R
import com.example.retrofitforecaster.model.WeatherViewModel

class MainActivity : AppCompatActivity() {
    lateinit var layoutManager: LinearLayoutManager
    private val adapter by lazy { WeatherAdapter() }
    private val model: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val button: Button = findViewById(R.id.search_button)
        button.setOnClickListener {
            search()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(addItemDecoration(recyclerView))
    }

    private fun observe() {
        model.weatherList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun search() {
        val etSearch: EditText = findViewById(R.id.search_edit_text)
        model.getWeather(etSearch.text.toString())
        adapter.submitList(model.weatherList.value)
        adapter.notifyDataSetChanged()
    }
}
