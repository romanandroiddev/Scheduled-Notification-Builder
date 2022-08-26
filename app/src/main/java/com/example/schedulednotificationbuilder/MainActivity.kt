package com.example.schedulednotificationbuilder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import qq.nukus.schedulednotificationbuilder.ScheduledNotification

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val onetime = findViewById<AppCompatButton>(R.id.one_time)
        onetime.setOnClickListener {
            ScheduledNotification.Builder(this)
                .setOneTimeNotification()
                .setContentTitle("It is a sample title")
                .setContentBody("One Time Notification's body")
                .setNotificationIcon(R.drawable.ic_kunduz_ic)
                .setStartTime(System.currentTimeMillis() + 120000L)
                .build()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }

        val repeated = findViewById<AppCompatButton>(R.id.repeated)
        repeated.setOnClickListener {
            ScheduledNotification.Builder(this)
                .setRepeatedNotifiaction()
                .setContentTitle("It is a sample title")
                .setContentBody("Repeated Notification's body")
                .setNotificationIcon(R.drawable.ic_kunduz_ic)
                .setStartTime(System.currentTimeMillis() + 120000L)
                .setInterval(60000L)
                .setRepeatingCount(5)
                .build()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
        val infinitive = findViewById<AppCompatButton>(R.id.infinitive)
        infinitive.setOnClickListener {
            ScheduledNotification.Builder(this)
                .setInfinitiveNotification()
                .setContentTitle("It is a sample title")
                .setContentBody("Infinitive Notification's body")
                .setNotificationIcon(R.drawable.ic_kunduz_ic)
                .setInterval(120000L)
                .setStartTime(System.currentTimeMillis() + 120000L)
                .build()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }

    }
}