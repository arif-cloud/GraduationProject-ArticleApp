package com.example.articleapp.domain.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.articleapp.R
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.use_case.article.GetAllArticles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

class NotificationWorker @Inject constructor(
    private val getAllArticles: GetAllArticles,
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private var notificationId = 0

    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                val dailyArticle = getAllArticles().random()
                sendNotification(notificationId, dailyArticle)
            }
            notificationId++
            Result.success()
        } catch (e : Exception) {
            Result.failure()
        }
    }

    private fun sendNotification(notificationId: Int, dailyArticle : Article) {
        val image = BitmapFactory.decodeStream(URL(dailyArticle.imageUrl).openConnection().getInputStream())
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setContentTitle("Daily Article")
            .setContentText(dailyArticle.title)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notificationId, notification.build())
    }
    companion object {
        const val NOTIFICATION_CHANNEL = "notification_channel"
        const val NOTIFICATION_NAME = "ArticleApp"
    }
}