package qq.nukus.schedulednotificationbuilder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import qq.nukus.schedulednotificationbuilder.constants.*
import qq.nukus.schedulednotificationbuilder.exceptions.UninitializedValueException
import qq.nukus.schedulednotificationbuilder.receivers.NotificationReceiver
import qq.nukus.schedulednotificationbuilder.receivers.RepeatedNotification
import kotlin.random.Random

open class ScheduledNotification {

    class Builder(private val context: Context) {
        private var fragment: Fragment? = null
        private var title: String? = null
        private var body: String? = null

        private var interval: Long? = null

        var notificationType = NotificationType.ONE_TIME
        private var startTime: Long? = null
        private var count: Int? = null

        var notificationIcon: Int? = null


        constructor(fragment: Fragment) : this(fragment.requireActivity()) {
            this.fragment = fragment
        }

        fun setContentTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setInterval(value: Long): Builder {
            this.interval = value
            return this
        }


        fun setContentBody(body: String): Builder {
            this.body = body
            return this
        }

        fun setStartTime(startTime: Long): Builder {
            this.startTime = startTime
            return this
        }

        fun setRepeatingCount(count: Int): Builder {
            this.count = count
            return this
        }

        fun setNotificationIcon(icon: Int): Builder {
            this.notificationIcon = icon
            return this
        }

        fun setRepeatedNotifiaction(): Builder {
            this.notificationType = NotificationType.REPEATED
            return this
        }

        fun setOneTimeNotification(): Builder {
            this.notificationType = NotificationType.ONE_TIME
            return this
        }

        fun setInfinitiveNotification(): Builder {
            this.notificationType = NotificationType.UNLIMITED
            return this
        }


        fun build() {
            if (notificationType == NotificationType.ONE_TIME) {
                if (title == null) {
                    throw UninitializedValueException("Uninitialized Title")
                }
                if (body == null) {
                    throw UninitializedValueException("Uninitialized Body")
                }
                if (notificationIcon == null) {
                    throw UninitializedValueException("Uninitialized Icon")
                }

                if (title != null && body != null && startTime != null && notificationIcon != null) {
                    val pendingCode = Random.nextInt(12000, 16000)

                    val intent = Intent(context, NotificationReceiver::class.java)
                    intent.putExtra(TITLE_EXTRA, title)
                    intent.putExtra(BODY_EXTRA, body)
                    intent.putExtra(ICON_EXTRA, notificationIcon)

                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        pendingCode,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC,
                            startTime ?: System.currentTimeMillis(),
                            pendingIntent
                        )
                    }
                }
            } else if (notificationType == NotificationType.REPEATED) {
                if (title == null) {
                    throw UninitializedValueException("Uninitialized Title")
                }
                if (body == null) {
                    throw UninitializedValueException("Uninitialized Body")
                }
                if (notificationIcon == null) {
                    throw UninitializedValueException("Uninitialized Icon")
                }
                if (count == null) {
                    throw UninitializedValueException("Uninitialized Repeat Count")
                }

                if (title != null && body != null && startTime != null && notificationIcon != null && count != null && interval != null) {
                    val pendingCode = Random.nextInt(1, 6000)

                    val intent = Intent(context, RepeatedNotification::class.java)

                    val time = startTime!! + interval!! * count!! + 1000L
                    intent.putExtra(TITLE_EXTRA, title)
                    intent.putExtra(BODY_EXTRA, body)
                    intent.putExtra(ICON_EXTRA, notificationIcon)
                    intent.putExtra(
                        END_TIME_EXTRA,
                        time
                    )

                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        pendingCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    alarmManager.setRepeating(
                        AlarmManager.RTC,
                        startTime ?: System.currentTimeMillis(),
                        interval ?: AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )
                }
            } else {
                Log.d("TTTT", "BUILD() FUNCTION INFINITIVE")
                if (title == null) {
                    throw UninitializedValueException("Uninitialized Title")
                }
                if (body == null) {
                    throw UninitializedValueException("Uninitialized Body")
                }
                if (startTime == null) {
                    throw UninitializedValueException("Uninitialized StartTime")
                }
                if (notificationIcon == null) {
                    throw UninitializedValueException("Uninitialized Icon")
                }

                if (title != null && body != null && startTime != null && notificationIcon != null && interval != null) {
                    val pendingCode = Random.nextInt(6000, 12000)

                    val intent = Intent(context, NotificationReceiver::class.java)
                    intent.putExtra(TITLE_EXTRA, title)
                    intent.putExtra(BODY_EXTRA, body)
                    intent.putExtra(ICON_EXTRA, notificationIcon)

                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        pendingCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setRepeating(
                            AlarmManager.RTC,
                            startTime ?: System.currentTimeMillis(),
                            interval ?: AlarmManager.INTERVAL_DAY,
                            pendingIntent
                        )
                    }
                }

            }
        }
    }


}
