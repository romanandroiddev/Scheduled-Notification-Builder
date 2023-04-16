package com.example.schedulednotificationapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class OneTimeNotificationReciever: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = NotificationCompat.Builder(context!!, "HELLO_OD")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("IT IS TITLE")
            .setContentText("IT BODY YOU KNOW THAT")
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(
                "HELLO_OD",
                "NOTIFICATION",
                NotificationManager.IMPORTANCE_HIGH
            ))

        }

        notificationManager.notify("TTTT",Random.nextInt(0, 40000), notification)
    }
}