package com.knightua.unzipistic.managers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.knightua.unzipistic.R
import com.knightua.unzipistic.databinding.models.FileItem
import com.knightua.unzipistic.ui.activities.main.MainActivity
import timber.log.Timber

class ProgressManager(private val context: Context) {

    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    fun updateProgress(fileItem: FileItem, progress: Long) {
        Timber.i("Progress: $progress %")

        if (progress < 100) {
            val notification =
                NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                    .setSmallIcon(R.drawable.ic_archive_primary_24dp)
                    .setContentTitle("Extracting ${fileItem.name}")
                    .setContentText("Progress: $progress %")
                    .setProgress(100, progress.toInt(), false)
                    .build()

            notificationManagerCompat.notify(NOTIFY_ID, notification)
        } else
            showFinish(fileItem)
    }

    fun showFinish(fileItem: FileItem) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notification =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_archive_primary_24dp)
                .setContentTitle("File ${fileItem.name} success extracted")
                .setContentIntent(pendingIntent)

        notification.priority = NotificationCompat.PRIORITY_HIGH

        notificationManagerCompat.notify(NOTIFY_ID, notification.build())
    }

    companion object {
        const val NOTIFY_ID = 1997
    }
}