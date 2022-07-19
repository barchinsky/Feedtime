package com.borchik.infrastructure.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FeedingDao {

    @Query("SELECT * FROM feedingentity ORDER BY feeding_timestamp DESC")
    suspend fun getAll(): List<FeedingEntity>

    @Insert
    suspend fun add(feeding: FeedingEntity)

    @Query("DELETE FROM feedingentity")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(feeding: FeedingEntity)
}