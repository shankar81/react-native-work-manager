package com.rnworkmanager

import android.util.Log
import androidx.work.*
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private const val MODULE_NAME = "SchedulingMaster"
private const val TAG = "SchedulingManager"

class SchedulingManager(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule() {

    override fun getName() = MODULE_NAME

    @ReactMethod
    fun setUpWorker(
            uniqueName: String,
            type: String = "SINGLE_SHOT",
            initialDelay: Int = 15,
            initialTimeUnit: String,
            repeatInterval: Int = 15,
            intervalUnit: String,
            notificationTitle: String,
            notificationDesc: String,
            notificationColor: String? = "",
            cb: Callback,
    ) {
        Log.d(TAG, """ setUpWorker
            uniqueName: $uniqueName
            type: $type
            initialDelay: $initialDelay
            initialTimeUnit: $initialTimeUnit
            repeatInterval: $repeatInterval
            intervalUnit: $intervalUnit
            cb: $cb
        """.trimIndent())
        val unit = getTimeUnit(initialTimeUnit)
        val intervalTimeUnit = getTimeUnit(intervalUnit)

        val constraints = Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val data = Data.Builder()
                .putString(WORK_NOTIFICATION_TITLE, notificationTitle)
                .putString(WORK_NOTIFICATION_DESCRIPTION, notificationDesc)
                .putString(WORK_NOTIFICATION_COLOR, notificationColor)
                .build()

        val oneTimeRequest = OneTimeWorkRequest
                .Builder(MyWorkRequest::class.java)
                .setInitialDelay(initialDelay.toLong(), unit)
                .setInputData(data)
                .build()

        val periodicRequest = PeriodicWorkRequest
                .Builder(MyWorkRequest::class.java, repeatInterval.toLong(), intervalTimeUnit)
                .setInitialDelay(initialDelay.toLong(), unit)
                .setInputData(data)
                .setConstraints(constraints)
                .build()

        if (type == "SINGLE_SHOT") {
            WorkManager
                    .getInstance(reactContext)
                    .enqueueUniqueWork(uniqueName, ExistingWorkPolicy.REPLACE, oneTimeRequest)

            cb(oneTimeRequest.id.toString())
        } else {
            WorkManager
                    .getInstance(reactContext)
                    .enqueueUniquePeriodicWork(uniqueName, ExistingPeriodicWorkPolicy.REPLACE, periodicRequest)
            cb(periodicRequest.id.toString())
        }

        Log.d(TAG, "setUpWorker: $type")
    }

    @ReactMethod
    fun getWorkInfoById(id: String, cb: Callback) {
        val info = WorkManager.getInstance(reactContext).getWorkInfoById(UUID.fromString(id))
        info.addListener({
            Log.d(TAG, "getWorkInfoById: $info")
        }, Executors.newSingleThreadExecutor())
    }

    @ReactMethod
    fun cancelJobById(id: String, cb: Callback) {
        WorkManager.getInstance(reactContext).cancelWorkById(UUID.fromString(id))
        cb(true)
    }

    private fun getTimeUnit(unit: String): TimeUnit = when (unit) {
        "MILLISECONDS" -> TimeUnit.MILLISECONDS
        "SECONDS" -> TimeUnit.SECONDS
        "MINUTES" -> TimeUnit.MINUTES
        "HOURS" -> TimeUnit.HOURS
        else -> TimeUnit.SECONDS
    }

}