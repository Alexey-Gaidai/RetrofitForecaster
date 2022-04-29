package com.example.retrofitforecaster

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlin.concurrent.thread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var layoutManager: LinearLayoutManager
    private val adapter by lazy { Adapter() }
    private val model: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        observe()
        buttonListener()
        GlobalScope.launch {
            connectToDb()
        }
    }

    private fun connectToDb() {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration().build()
        val userDao = db.userDao()
        val users: List<Weather> = userDao.getAll()
        userDao.insertAll(convertToDataBaseStruct())
        Log.d("room", users.toString())
    }

    fun convertToDataBaseStruct(): List<Weather> {
        val weatherToSave: MutableList<Weather> = mutableListOf()
        model.weatherList.value?.forEach { weather ->
            val weather = Weather(weather.main.temp,weather.dt_txt, weather.weather?.get(0)?.description)
            weatherToSave.add(weather)
        }
        return weatherToSave
    }

    private fun setItemDecoration(rv: RecyclerView): DividerItemDecoration {
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
        recyclerView.addItemDecoration(setItemDecoration(recyclerView))
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
