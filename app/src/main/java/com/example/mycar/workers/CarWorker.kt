package com.example.mycar.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val TAG = "CarWorker"
class CarWorker (ctx: Context, params: WorkerParameters) : Worker(ctx, params)  {
    override fun doWork(): Result {
        val appContext = applicationContext

        //to finish
        //makeStatusNotification("Cutting to do", appContext)

        return try {
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error message")
            Result.failure()
        }
    }
}