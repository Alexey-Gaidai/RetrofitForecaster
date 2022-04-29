package com.example.retrofitforecaster


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ContactItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem

}

class ViewHolderCold(view: View) : RecyclerView.ViewHolder(view) {
    private val dateTime: TextView = view.findViewById(R.id.tvDateTime)
    private val temperature: TextView = view.findViewById(R.id.tvTemperature)
    private val description: TextView = view.findViewById(R.id.tvDescription)

    fun bindTo(weather: ListItem, position: Int) {
        temperature.text = weather.main.temp.toString()
        dateTime.text = weather.dt_txt
        description.text = weather.weather?.get(0)?.description
    }
}

class ViewHolderHot(view: View) : RecyclerView.ViewHolder(view) {
    private val datetime: TextView = view.findViewById(R.id.tvDateTime)
    private val temperature: TextView = view.findViewById(R.id.tvTemperature)
    private val description: TextView = view.findViewById(R.id.tvDescription)

    fun bindTo(weather: ListItem, position: Int) {
        temperature.text = weather.main.temp.toString()
        datetime.text = weather.dt_txt
        description.text = weather.weather?.get(0)?.description
    }
}

class Adapter : ListAdapter<ListItem, RecyclerView.ViewHolder>(ContactItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false)
            view.setBackgroundColor(Color.rgb(240, 128, 128))
            ViewHolderHot(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false)
            view.setBackgroundColor(Color.rgb(135, 206, 235))
            ViewHolderCold(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = currentList[position]
        if (data.main.temp > 0) {
            val viewholderhot: ViewHolderHot = holder as ViewHolderHot
            viewholderhot.bindTo(data, position)
        } else {
            val viewholdercold: ViewHolderCold = holder as ViewHolderCold
            viewholdercold.bindTo(data, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].main.temp > 0) {
            0
        } else {
            1
        }
    }
}

