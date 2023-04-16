package us.romanandroiddev.scheduled_notification_builder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.fragment.app.Fragment
import us.romanandroiddev.scheduled_notification_builder.data.models.NotificationType
import us.romanandroiddev.scheduled_notification_builder.receivers.NotificationReceiver
import us.romanandroiddev.scheduled_notification_builder.receivers.RepeatedNotificationReceiver
import us.romanandroiddev.scheduled_notification_builder.utils.END_TIME_EXTRA
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_DESCRIPTION_DATA
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_ICON
import us.romanandroiddev.scheduled_notification_builder.utils.EXTRA_TITLE_DATA
import kotlin.random.Random


/*
Added by romanandroiddev in 16.04.2023
*/
class ScheduledNotification {
    class Builder(private val context: Context) {
        private var title: String = context.getString(R.string.app_name)
        private var description: String? = null

        private var interval: Long = AlarmManager.INTERVAL_DAY

        private var notificationType = NotificationType.INSTANT
        private var scheduledTime: Long? = null
        private var count: Int = 1

        private var notificationIcon: Int = R.drawable.ic_launcher_foreground

        constructor(fragment: Fragment) : this(fragment.requireContext()) {

        }


        fun setType(type: NotificationType): Builder {
            this.notificationType = type
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun setIcon(icon: Int): Builder {
            this.notificationIcon = icon
            return this
        }

        fun setInterval(interval: Long): Builder {
            this.interval = interval
            return this
        }

        fun setScheduledTime(milliseconds: Long): Builder {
            this.scheduledTime = milliseconds
            return this
        }

        fun build() {
            when (notificationType) {
                NotificationType.INSTANT -> {
                    val intent = Intent(context, NotificationReceiver::class.java)
                    intent.putExtra(EXTRA_TITLE_DATA, title)
                    intent.putExtra(EXTRA_DESCRIPTION_DATA, description)
                    intent.putExtra(EXTRA_ICON, notificationIcon)
                    context.sendBroadcast(intent)
                }
                NotificationType.SCHEDULED -> {
                    if (scheduledTime == null) {
                        throw Exception("When notification type is SCHEDULED, scheduledTime must not be empty....")
                    }

                    if (scheduledTime != null) {
                        val intent = Intent(context, NotificationReceiver::class.java)
                        intent.putExtra(EXTRA_TITLE_DATA, title)
                        intent.putExtra(EXTRA_DESCRIPTION_DATA, description)
                        intent.putExtra(EXTRA_ICON, notificationIcon)

                        val pendingCode = Random.nextInt(1, 6000)

                        val alarmManager =
                            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                        val pendingIntent = PendingIntent.getBroadcast(
                            context, pendingCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
                        )

                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            scheduledTime ?: System.currentTimeMillis(),
                            pendingIntent
                        )
                    }
                }
                NotificationType.INFINITY -> {
                    val pendingCode = Random.nextInt(6000, 12000)

                    val intent = Intent(context, NotificationReceiver::class.java)
                    intent.putExtra(EXTRA_TITLE_DATA, title)
                    intent.putExtra(EXTRA_DESCRIPTION_DATA, description)
                    intent.putExtra(EXTRA_ICON, notificationIcon)

                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    val pendingIntent = PendingIntent.getBroadcast(
                        context, pendingCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setRepeating(
                            AlarmManager.RTC,
                            scheduledTime ?: System.currentTimeMillis(),
                            interval,
                            pendingIntent
                        )
                    }
                }
                NotificationType.SCHEDULED_WITH_REPEAT_COUNT -> {
                    val pendingCode = Random.nextInt(1, 6000)

                    val intent = Intent(context, RepeatedNotificationReceiver::class.java)

                    val time = scheduledTime?.plus(interval * count + 1000L)
                    intent.putExtra(EXTRA_TITLE_DATA, title)
                    intent.putExtra(EXTRA_DESCRIPTION_DATA, description)
                    intent.putExtra(EXTRA_ICON, notificationIcon)
                    intent.putExtra(
                        END_TIME_EXTRA, time
                    )

                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    val pendingIntent = PendingIntent.getBroadcast(
                        context, pendingCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    alarmManager.setRepeating(
                        AlarmManager.RTC,
                        scheduledTime ?: System.currentTimeMillis(),
                        interval,
                        pendingIntent
                    )
                }
            }
        }
    }
}