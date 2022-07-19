package com.borchik.dashboard.usecase

class FormatFeedingIntervalUseCase {

    suspend fun execute(earlierTimestamp: Long, laterTimestamp: Long): String {
        val intervalInMinutes = (laterTimestamp - earlierTimestamp) / 1_000 / 60
        val hours = (intervalInMinutes / 60).toInt()
        val remainingMinutes = intervalInMinutes%60

        return "${hours}h ${remainingMinutes}m"
    }
}