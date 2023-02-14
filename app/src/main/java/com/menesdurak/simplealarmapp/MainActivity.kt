package com.menesdurak.simplealarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var alarmTimePicker: TimePicker
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val button = findViewById<ToggleButton>(R.id.toggleButton)

        button.setOnClickListener {
            var time: Long
            if (button.isChecked) {
                Toast.makeText(this, "ALARM ON", Toast.LENGTH_SHORT).show()
                val calendar = Calendar.getInstance()

                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.hour)
                calendar.set(Calendar.MINUTE, alarmTimePicker.minute)

                val intent = Intent(this, AlarmReceiver::class.java)

                pendingIntent = PendingIntent.getBroadcast(this, 0 , intent, PendingIntent.FLAG_MUTABLE)

                time = (calendar.timeInMillis - calendar.timeInMillis%60000)
                if (System.currentTimeMillis() > time) {
                    if (Calendar.AM_PM == 0) {
                        time = time + (1000 * 60 * 60 * 12)
                    } else {
                        time = time + (1000 * 60 * 60 * 24)
                    }
                }

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 60000, pendingIntent)
            } else {
                alarmManager.cancel(pendingIntent)
                Toast.makeText(this, "ALARM OFF", Toast.LENGTH_SHORT).show()
            }
        }
    }


}