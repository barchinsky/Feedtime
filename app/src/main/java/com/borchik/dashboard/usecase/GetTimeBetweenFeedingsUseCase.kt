package com.borchik.dashboard.usecase

import com.borchik.infrastructure.db.FeedingRepository

class GetTimeBetweenFeedingsUseCase(
    private val feedingRepository: FeedingRepository,
    private val formatFeedingIntervalUseCase: FormatFeedingIntervalUseCase
) {

    suspend fun execute(): String =
        feedingRepository.getAll()
            .takeIf { it.size > 1 }
            ?.let {
                val interval = it.zip(it.subList(1, it.size))
                    .map { it.first.timestamp - it.second.timestamp }
                    .average()

                formatFeedingIntervalUseCase.execute(0, interval.toLong())
            } ?: "N/A"
}