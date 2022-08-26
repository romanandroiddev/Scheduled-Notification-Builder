package qq.nukus.schedulednotificationbuilder.receivers

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import qq.nukus.schedulednotificationbuilder.R
import qq.nukus.schedulednotificationbuilder.ScheduledNotification
import qq.nukus.schedulednotificationbuilder.constants.*
import kotlin.random.Random

class RepeatedNotification : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (System.currentTimeMillis() < intent?.getLongExtra(END_TIME_EXTRA, 0L)!!) {
            val notification =
                NotificationCompat.Builder(context!!, "${context.packageName}_REPEATED")
                    .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
                    .setContentText(intent.getStringExtra(BODY_EXTRA))
                    .setSmallIcon(intent.getIntExtra(ICON_EXTRA, R.drawable.ic_launcher_background))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .build()

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(createNotifChannel(context))

            }
            manager.notify(Random.nextInt(20000, 30000), notification)
        } else {
            val intentt = Intent(context, CancelNotificationReceiver::class.java)
            intentt.putExtra(TITLE_EXTRA, intent.getStringExtra(TITLE_EXTRA))
            intentt.putExtra(BODY_EXTRA, intent.getStringExtra(BODY_EXTRA))
            intentt.putExtra(
                ICON_EXTRA,
                intent.getIntExtra(ICON_EXTRA, R.drawable.ic_launcher_background)
            )
            intentt.putExtra(END_TIME_EXTRA, intent.getLongExtra(END_TIME_EXTRA, 0L))
            intentt.putExtra(
                PENDING_ALARM_STOP_CODE,
                intent.getIntExtra(PENDING_ALARM_STOP_CODE, 0)
            )
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                100,
                intentt,
                PendingIntent.FLAG_ONE_SHOT
            )
            val alarmManager =
                context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(
                AlarmManager.RTC,
                System.currentTimeMillis(),
                pendingIntent
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifChannel(context: Context): NotificationChannel {
        return NotificationChannel(
            "${context.packageName}_REPEATED",
            "REPEATED NOTIFICATION",
            NotificationManager.IMPORTANCE_HIGH
        )
    }
}