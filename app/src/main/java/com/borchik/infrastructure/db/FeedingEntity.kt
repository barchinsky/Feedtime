package com.borchik.infrastructure.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedingEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "feeding_timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "feeding_datetime")
    val dateTime: String,
    @ColumnInfo(name = "feeding_interval")
    val feedingInterval: String
)