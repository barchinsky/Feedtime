package com.borchik.infrastructure.db

class FeedingRepository(private val db: FeedingDatabase) {

    suspend fun getAll(): List<FeedingEntity> =
        db.feedingDao()
            .getAll()

    suspend fun add(feeding: FeedingEntity) =
        db.feedingDao()
            .add(feeding)

    suspend fun delete(feeding: FeedingEntity) =
        db.feedingDao()
            .delete(feeding)
}