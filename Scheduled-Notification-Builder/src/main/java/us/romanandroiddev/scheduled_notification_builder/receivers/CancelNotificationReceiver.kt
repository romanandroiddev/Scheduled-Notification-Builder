package us.romanandroiddev.scheduled_notification_builder.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import us.romanandroiddev.scheduled_notification_builder.utils.END_TIME_EXTRA
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_DESCRIPTION_DATA
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_TITLE_DATA
import us.romanandroiddev.scheduled_notification_builder.utils.PENDING_ALARM_STOP_CODE


/*
Added by romanandroiddev in 16.04.2023
*/
class CancelNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationIntent = Intent(context, RepeatedNotificationReceiver::class.java)
        notificationIntent.putExtra(EXTRA_TITLE_DATA, intent?.getStringExtra(EXTRA_TITLE_DATA))
        notificationIntent.putExtra(
            EXTRA_DESCRIPTION_DATA, intent?.getStringExtra(
                EXTRA_DESCRIPTION_DATA
            )
        )

        notificationIntent.putExtra(END_TIME_EXTRA, intent?.getLongExtra(END_TIME_EXTRA, 0L))
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            intent?.getIntExtra(PENDING_ALARM_STOP_CODE, 0)!!,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        alarmManager.cancel(pendingIntent)
    }
}