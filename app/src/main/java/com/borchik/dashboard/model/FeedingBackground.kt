package com.borchik.dashboard.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.borchik.feedtime.R

enum class FeedingBackground(val backgroundRes: Int, @ColorRes val textColor: Int, @DrawableRes val illustration: Int) {
    EARLY_MORNING(R.drawable.bg_early_morning, R.color.white, R.drawable.il_early_night),
    MORNING(R.drawable.bg_morning, R.color.white, R.drawable.il_early_night),
    DAY(R.drawable.bg_day, R.color.black, R.drawable.il_sunny),
    LUNCH(R.drawable.bg_lunchtime, R.color.black, R.drawable.il_lunch),
    EVENING(R.drawable.bg_evening, R.color.black, R.drawable.il_sunny),
    SUNSET(R.drawable.bg_sunset, R.color.black, R.drawable.il_lunch),
    EARLY_NIGHT(R.drawable.bg_early_night, R.color.light_grey, R.drawable.il_early_night),
    NIGHT(R.drawable.bg_late_night, R.color.white, R.drawable.il_night)
}