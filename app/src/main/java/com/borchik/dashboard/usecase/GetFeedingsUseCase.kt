package com.borchik.dashboard.usecase

import com.borchik.infrastructure.db.FeedingEntity
import com.borchik.infrastructure.db.FeedingRepository

class GetFeedingsUseCase(
    private val feedingRepository: FeedingRepository,
    private val formatFeedingIntervalUseCase: FormatFeedingIntervalUseCase
) {

    suspend fun execute(): List<FeedingEntity> {
        val feedings = feedingRepository.getAll()
        val lastFeedingIndex = feedings.size - 1

        return feedings.mapIndexed { index, feedingEntity ->
            val earlierTimestamp = if (index == lastFeedingIndex) {
                feedingEntity.timestamp
            } else {
                feedings[index + 1].timestamp
            }

            feedingEntity.copy(
                feedingInterval = formatFeedingIntervalUseCase.execute(
                    earlierTimestamp,
                    feedingEntity.timestamp
                )
            )
        }
    }

}