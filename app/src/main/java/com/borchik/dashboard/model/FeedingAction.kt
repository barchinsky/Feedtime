package com.borchik.dashboard.model

import com.borchik.feedtime.R

enum class FeedingAction(val resId: Int) {
    ADDED(R.string.feeding_added),
    REMOVED(R.string.feeding_removed),
    RESTORED(R.string.feeding_restored)
}