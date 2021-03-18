package com.rnworkmanager

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.facebook.react.HeadlessJsTaskService
import com.facebook.react.bridge.Arguments
import com.facebook.react.jstasks.HeadlessJsTaskConfig
import java.util.*
import kotlin.math.log

private const val TAG = "MyHeadlessService"

class MyHeadlessService : HeadlessJsTaskService() {

    override fun onHeadlessJsTaskStart(taskId: Int) {
        super.onHeadlessJsTaskStart(taskId)

        getTime(System.currentTimeMillis())
        Log.d(TAG, "onHeadlessJsTaskStart: ")
    }

    override fun getTaskConfig(intent: Intent?): HeadlessJsTaskConfig {
        val extras = intent?.extras ?: Bundle()
        extras.putString("Default Key", "Default Value")
        Log.d(TAG, "getTaskConfig: $extras")

        showNotification(extras.getString(WORK_NOTIFICATION_TITLE, "DEFAULT TITLE"),
                extras.getString(WORK_NOTIFICATION_DESCRIPTION, "DEFAULT DESCRIPTION"))

        return HeadlessJsTaskConfig(
                "MyTaskInJS",
                Arguments.fromBundle(extras),
                60000,  // timeout for the task
                true // optional: defines whether or not  the task is allowed in foreground. Default is false
        )
    }

    private fun showNotification(title: String, desc: String) {
        val notification = NotificationCompat
                .Builder(baseContext, MainApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.redbox_top_border_background)
                .setContentTitle(title)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

        startForeground(2, notification)
    }

    override fun onHeadlessJsTaskFinish(taskId: Int) {
        super.onHeadlessJsTaskFinish(taskId)
        getTime(System.currentTimeMillis())
        Log.d(TAG, "onHeadlessJsTaskFinish: ")
        stopSelf()
    }

    private fun getTime(time: Long) {
        Calendar.getInstance().apply {
            timeInMillis = time
            Log.d(TAG, "getTime: ${get(Calendar.HOUR_OF_DAY)}" +
                    " ${get(Calendar.MINUTE)} " +
                    " ${get(Calendar.SECOND)} " +
                    " ${get(Calendar.DATE)} ")
        }
    }

}