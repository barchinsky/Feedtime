package com.borchik.infrastructure.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FeedingEntity::class], version = 2)
abstract class FeedingDatabase : RoomDatabase() {

    abstract fun feedingDao(): FeedingDao
}