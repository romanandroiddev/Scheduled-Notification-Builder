package qq.nukus.schedulednotificationbuilder.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import qq.nukus.schedulednotificationbuilder.R
import qq.nukus.schedulednotificationbuilder.ScheduledNotification
import qq.nukus.schedulednotificationbuilder.constants.BODY_EXTRA
import qq.nukus.schedulednotificationbuilder.constants.ICON_EXTRA
import qq.nukus.schedulednotificationbuilder.constants.TITLE_EXTRA
import kotlin.random.Random

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification =
            NotificationCompat.Builder(context!!, "${context.packageName}_NOTIFICATION")
                .setContentTitle(intent?.getStringExtra(TITLE_EXTRA))
                .setContentText(intent?.getStringExtra(BODY_EXTRA))
                .setSmallIcon(
                    intent?.getIntExtra(ICON_EXTRA, R.drawable.ic_launcher_background)
                        ?: R.drawable.ic_launcher_background
                )
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build()

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(createNotifChannel(context))

        }
        manager.notify(Random.nextInt(30000, 40000), notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifChannel(context: Context): NotificationChannel {
        return NotificationChannel(
            "${context.packageName}_NOTIFICATION",
            "INFINITIVE NOTIFICATION",
            NotificationManager.IMPORTANCE_HIGH
        )
    }
}