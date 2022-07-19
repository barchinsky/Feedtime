package com.borchik.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.borchik.feedtime.databinding.FeedingBinding
import com.borchik.infrastructure.db.FeedingEntity

class FeedingAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<FeedingViewHolder>() {

    private var feedings: List<FeedingEntity> = emptyList()

    override fun getItemCount(): Int =
        feedings.size

    fun addFeedings(newFeedings: List<FeedingEntity>) {
        val oldFeedings = feedings.toMutableList()
        feedings = newFeedings
        DiffUtil.calculateDiff(FeedingDiffUtilCallback(oldFeedings, feedings))
            .dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingViewHolder =
        FeedingViewHolder(
            binding = FeedingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FeedingViewHolder, position: Int) {
        holder.bind(feedings[position])
    }
}