package com.borchik.addFeeding.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.borchik.dashboard.usecase.CreateFeedingEntityUseCase
import com.borchik.infrastructure.db.FeedingRepository
import com.borchik.infrastructure.utils.pushNotification.NotificationsDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.Calendar

class AddFeedingService : Service() {

    private val feedingsRepository by inject<FeedingRepository>()

    private val createFeedingEntityUseCase by inject<CreateFeedingEntityUseCase>()

    private val notificationsDelegate by inject<NotificationsDelegate>()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val feeding = createFeedingEntityUseCase.execute(Calendar.getInstance().timeInMillis)
                feedingsRepository.add(feeding)
                notificationsDelegate.showFeedingAdded("Added at ${feeding.dateTime}")
                Timber.i("Feeding added!")
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}