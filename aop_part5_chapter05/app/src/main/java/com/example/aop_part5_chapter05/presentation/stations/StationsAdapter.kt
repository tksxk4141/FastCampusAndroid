package com.example.aop_part5_chapter05.presentation.stations

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.aop_part5_chapter05.databinding.ItemStationBinding
import com.example.aop_part5_chapter05.domain.Station
import com.example.aop_part5_chapter05.extension.dip
import com.example.aop_part5_chapter05.presentation.view.Badge

class StationsAdapter : RecyclerView.Adapter<StationsAdapter.ViewHolder>() {

    var data: List<Station> = emptyList()

    var onItemClickListener: ((Station) -> Unit)? = null
    var onFavoriteClickListener: ((Station) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(data[adapterPosition])
            }

            binding.favorite.setOnClickListener {
                onFavoriteClickListener?.invoke(data[adapterPosition])
            }
        }

        fun bind(station: Station) {
            binding.badgeContainer.removeAllViews()

            station.connectedSubways
                .forEach { subway ->
                    binding.badgeContainer.addView(
                        Badge(binding.root.context).apply {
                            badgeColor = subway.color
                            text = subway.label
                            layoutParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    rightMargin = dip(6f)
                                }
                        }
                    )
                }
            binding.stationNameTextView.text = station.name
            binding.favorite.isChecked = station.isFavorited
        }
    }
}