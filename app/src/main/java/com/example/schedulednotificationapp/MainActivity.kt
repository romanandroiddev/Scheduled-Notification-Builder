package com.example.schedulednotificationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.schedulednotificationapp.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import us.romanandroiddev.scheduled_notification_builder.ScheduledNotification
import us.romanandroiddev.scheduled_notification_builder.data.models.NotificationType
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnShowNotification.setOnClickListener {
            ScheduledNotification.Builder(this).setType(NotificationType.INSTANT)
                .setTitle("Hello it is title").setDescription("It is simple discription")
                .setIcon(R.drawable.ic_launcher_background).build()
        }

        binding.btnScheduledNotification.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder().build()


            timePicker.show(supportFragmentManager, "TTTT")
            timePicker.addOnPositiveButtonClickListener {

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MINUTE, timePicker.minute)
                calendar.set(Calendar.HOUR, timePicker.hour)

                ScheduledNotification.Builder(this).setType(NotificationType.SCHEDULED)
                    .setTitle("Scheduled notification title")
                    .setDescription("It is description for scheduled notification")
                    .setIcon(R.drawable.ic_launcher_background)
                    .setScheduledTime(calendar.timeInMillis).build()
                Toast.makeText(this,"SUCCESFULLY ADDED TO QUEUE", Toast.LENGTH_SHORT).show()
            }
        }

    }
}