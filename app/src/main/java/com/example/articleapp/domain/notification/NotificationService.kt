package com.example.articleapp.domain.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.articleapp.MainActivity
import com.example.articleapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Token", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("Message", message.notification?.title.toString())
        showNotification(message)
    }

    private fun showNotification(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "notification_channel",
            "default",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notification = NotificationCompat.Builder(this, channel.id)
            .setAutoCancel(true)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentIntent(pendingIntent)
            .build()
        manager.createNotificationChannel(channel)
        manager.notify(0, notification)
    }

}