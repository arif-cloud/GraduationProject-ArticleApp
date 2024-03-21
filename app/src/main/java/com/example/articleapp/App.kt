package com.example.articleapp

import android.app.Application
import android.content.SharedPreferences
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.articleapp.domain.notification.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        val isWorkScheduled = sharedPreferences.getBoolean("is_work_scheduled", false)
        if (!isWorkScheduled) {
            scheduleNotification()
            sharedPreferences.edit().putBoolean("is_work_scheduled", true).apply()
        }
    }

    private fun scheduleNotification() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setConstraints(constraints).build()
        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
    }

}