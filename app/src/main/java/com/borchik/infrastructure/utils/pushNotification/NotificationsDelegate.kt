package com.borchik.infrastructure.utils.pushNotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.borchik.feedtime.MainActivity
import com.borchik.feedtime.R

class NotificationsDelegate(
    private val context: Context
) {

    fun showFeedingAdded(message: String) {
        createFeedingAddedChannel()

        val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notificationBuilder = NotificationCompat
            .Builder(
                context,
                NotificationsChannel.FEEDING_ADDED.id
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Feeding added!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(PendingIntent.getActivity(context, 0, mainActivityIntent, PendingIntent.FLAG_IMMUTABLE))
            .setAutoCancel(true)

        NotificationManagerCompat.from(context)
            .notify(NotificationsChannel.FEEDING_ADDED.id.hashCode(), notificationBuilder.build())
    }

    private fun createFeedingAddedChannel() {
        NotificationManagerCompat.from(context)
            .createNotificationChannel(
                NotificationChannel(
                    NotificationsChannel.FEEDING_ADDED.id,
                    NotificationsChannel.FEEDING_ADDED.description,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
    }
}