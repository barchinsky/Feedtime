package com.borchik.dashboard.adapter

import androidx.recyclerview.widget.RecyclerView
import com.borchik.dashboard.model.FeedingBackground
import com.borchik.feedtime.databinding.FeedingBinding
import com.borchik.infrastructure.db.FeedingEntity
import java.util.Calendar

class FeedingViewHolder(private val binding: FeedingBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feeding: FeedingEntity) {
        binding.feeding = feeding

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = feeding.timestamp

        val background = when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in (4..7) -> FeedingBackground.EARLY_MORNING
            in (8..10) -> FeedingBackground.MORNING
            in (11..13) -> FeedingBackground.DAY
            in (14..15) -> FeedingBackground.LUNCH
            in (16..17) -> FeedingBackground.EVENING
            in (18..19) -> FeedingBackground.SUNSET
            in (20..22) -> FeedingBackground.EARLY_NIGHT
            else -> FeedingBackground.NIGHT
        }

        binding.apply {
            container.setBackgroundResource(background.backgroundRes)
            feedingDate.setTextColor(binding.root.context.getColor(background.textColor))
            interval.setTextColor(binding.root.context.getColor(background.textColor))
            illustration.setBackgroundResource(background.illustration)
        }
    }
}