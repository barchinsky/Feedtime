package com.borchik.infrastructure.di

import android.content.Context
import android.os.Vibrator
import androidx.room.Room
import com.borchik.infrastructure.db.FeedingDatabase
import com.borchik.infrastructure.db.FeedingRepository
import com.borchik.infrastructure.utils.DateFormatUtils
import com.borchik.infrastructure.utils.pushNotification.NotificationsDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object InfrastructureInjectionModule {

    val module = module {

        single(createdAtStart = true) {
            Room
                .databaseBuilder(
                    androidContext(),
                    FeedingDatabase::class.java,
                    "feedings"
                )
                .enableMultiInstanceInvalidation()
                .build()
        }

        single {
            FeedingRepository(get())
        }

        factory {
            androidContext().getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        single {
            NotificationsDelegate(
                context = get()
            )
        }

        factory {
            DateFormatUtils()
        }
    }
}