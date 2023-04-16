package us.romanandroiddev.scheduled_notification_builder.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import us.romanandroiddev.scheduled_notification_builder.R
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_DESCRIPTION_DATA
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_ICON
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_TITLE_DATA
import kotlin.random.Random


/*
Added by romanandroiddev in 16.04.2023
*/
internal class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification =
            NotificationCompat.Builder(context!!, "${context.packageName}_NOTIFICATION")
                .setSmallIcon(
                    intent?.getIntExtra(EXTRA_ICON, R.drawable.ic_launcher_foreground)
                        ?: R.drawable.ic_launcher_foreground
                ).setContentTitle(intent?.getStringExtra(EXTRA_TITLE_DATA))
                .setContentText(intent?.getStringExtra(EXTRA_DESCRIPTION_DATA)).build()


        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "${context.packageName}_NOTIFICATION",
                    "Notifications by ${context.packageName}",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        notificationManager.notify("TTTT", Random.nextInt(0, 40000), notification)
    }
}