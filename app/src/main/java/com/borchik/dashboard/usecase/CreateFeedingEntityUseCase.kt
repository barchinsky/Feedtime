package com.borchik.dashboard.usecase

import com.borchik.infrastructure.db.FeedingEntity
import java.text.DateFormat
import java.util.Date
import java.util.UUID

class CreateFeedingEntityUseCase {

    suspend fun execute(creationTimestamp: Long): FeedingEntity {
        return FeedingEntity(
            UUID.randomUUID()
                .toString(),
            creationTimestamp,
            formatDate(creationTimestamp),
            ""
        )
    }

    private fun formatDate(timestamp: Long): String =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
            .format(Date(timestamp))
}