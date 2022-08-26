package qq.nukus.schedulednotificationbuilder.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import qq.nukus.schedulednotificationbuilder.constants.BODY_EXTRA
import qq.nukus.schedulednotificationbuilder.constants.END_TIME_EXTRA
import qq.nukus.schedulednotificationbuilder.constants.PENDING_ALARM_STOP_CODE
import qq.nukus.schedulednotificationbuilder.constants.TITLE_EXTRA

class CancelNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intentt = Intent(context, RepeatedNotification::class.java)
        intentt.putExtra(TITLE_EXTRA, intent?.getStringExtra(TITLE_EXTRA))
        intentt.putExtra(BODY_EXTRA, intent?.getStringExtra(BODY_EXTRA))
        intentt.putExtra(END_TIME_EXTRA, intent?.getLongExtra(END_TIME_EXTRA, 0L))
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