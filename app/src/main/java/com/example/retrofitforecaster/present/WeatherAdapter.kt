package com.example.retrofitforecaster.present

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitforecaster.ListItem
import com.example.retrofitforecaster.R
import com.example.retrofitforecaster.data.WeatherDataUI
import com.example.retrofitforecaster.databinding.RviewItemBinding
import kotlin.math.roundToInt

private const val COLD_WEATHER = 0
private const val HOT_WEATHER = 1

class WeatherItemDiffCallback : DiffUtil.ItemCallback<WeatherDataUI>() {
    override fun areItemsTheSame(
        oldItem: WeatherDataUI,
        newItem: WeatherDataUI
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: WeatherDataUI,
        newItem: WeatherDataUI
    ): Boolean = oldItem == newItem

}

class WeatherAdapter :
    ListAdapter<WeatherDataUI, RecyclerView.ViewHolder>(WeatherItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == HOT_WEATHER) {
            WeatherViewHolderHot(binding)
        } else {
            WeatherViewHolderCold(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HOT_WEATHER) {
            (holder as WeatherViewHolderHot).bind(currentList[position])
        } else {
            (holder as WeatherViewHolderCold).bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].temperature > 0) HOT_WEATHER else COLD_WEATHER
    }
}

class WeatherViewHolderHot(private val binding: RviewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(itemWeather: WeatherDataUI) {
        binding.tvTemperature.text = itemWeather.temperature.roundToInt().toString()
        binding.tvPressure.text = itemWeather.pressure.toString()
        binding.tvDateTime.text = itemWeather.dateTime
        Glide
            .with(binding.root)
            .load(itemWeather.iconLink)
            .into(binding.viewIcon)
        binding.root.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                R.color.weather_hot
            )
        )
    }
}

class WeatherViewHolderCold(private val binding: RviewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(itemWeather: WeatherDataUI) {
        binding.tvTemperature.text = itemWeather.temperature.roundToInt().toString()
        binding.tvPressure.text = itemWeather.pressure.toString()
        binding.tvDateTime.text = itemWeather.dateTime
        Glide
            .with(binding.root)
            .load(itemWeather.iconLink)
            .into(binding.viewIcon)
        binding.root.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                R.color.weather_cold
            )
        )
    }
}