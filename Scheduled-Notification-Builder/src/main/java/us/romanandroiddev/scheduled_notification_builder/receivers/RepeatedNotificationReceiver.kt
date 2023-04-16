package us.romanandroiddev.scheduled_notification_builder.receivers

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import us.romanandroiddev.scheduled_notification_builder.R
import us.romanandroiddev.scheduled_notification_builder.utils.*
import us.romanandroiddev.scheduled_notification_builder.utils.END_TIME_EXTRA
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_DESCRIPTION_DATA
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_ICON
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_TITLE_DATA
import kotlin.random.Random


/*
Added by romanandroiddev in 16.04.2023
*/
class RepeatedNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (System.currentTimeMillis() < intent?.getLongExtra(END_TIME_EXTRA, 0L)!!) {
            val notification =
                NotificationCompat.Builder(context!!, "${context.packageName}_REPEATED")
                    .setContentTitle(intent.getStringExtra(EXTRA_TITLE_DATA))
                    .setContentText(intent.getStringExtra(EXTRA_DESCRIPTION_DATA))
                    .setSmallIcon(intent.getIntExtra(EXTRA_ICON, R.drawable.ic_launcher_foreground))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH).build()

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(
                    NotificationChannel(
                        "${context.packageName}_REPEATED",
                        "REPEATED NOTIFICATIONS by ${context.packageName}",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )

            }
            manager.notify(Random.nextInt(20000, 30000), notification)
        } else {
            val intentt = Intent(context, CancelNotificationReceiver::class.java)
            intentt.putExtra(EXTRA_TITLE_DATA, intent.getStringExtra(EXTRA_TITLE_DATA))
            intentt.putExtra(EXTRA_DESCRIPTION_DATA, intent.getStringExtra(EXTRA_DESCRIPTION_DATA))
            intentt.putExtra(
                EXTRA_ICON, intent.getIntExtra(EXTRA_ICON, R.drawable.ic_launcher_foreground)
            )
            intentt.putExtra(END_TIME_EXTRA, intent.getLongExtra(END_TIME_EXTRA, 0L))
            intentt.putExtra(
                PENDING_ALARM_STOP_CODE, intent.getIntExtra(PENDING_ALARM_STOP_CODE, 0)
            )
            val pendingIntent = PendingIntent.getBroadcast(
                context, 100, intentt, PendingIntent.FLAG_ONE_SHOT
            )
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(
                AlarmManager.RTC, System.currentTimeMillis(), pendingIntent
            )
        }
    }
}