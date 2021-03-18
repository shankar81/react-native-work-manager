package com.rnworkmanager

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.facebook.react.HeadlessJsTaskService

private const val TAG = "MyWorkRequest"
const val WORK_NOTIFICATION_TITLE = "WORK_NOTIFICATION_TITLE"
const val WORK_NOTIFICATION_DESCRIPTION = "WORK_NOTIFICATION_DESCRIPTION"

class MyWorkRequest(context: Context, private val workParams: WorkerParameters) : Worker(context, workParams) {
    override fun doWork(): Result {
        val service = Intent(applicationContext, MyHeadlessService::class.java).apply {
            putExtra(WORK_NOTIFICATION_TITLE, workParams.inputData.getString(WORK_NOTIFICATION_TITLE))
            putExtra(WORK_NOTIFICATION_DESCRIPTION, workParams.inputData.getString(WORK_NOTIFICATION_DESCRIPTION))
        }

        HeadlessJsTaskService.acquireWakeLockNow(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "doWork: Greater Oreo")
            applicationContext.startForegroundService(service)
        } else {
            Log.d(TAG, "doWork: Not Oreo")
            applicationContext.startService(service)
        }
        return Result.success()
    }
}