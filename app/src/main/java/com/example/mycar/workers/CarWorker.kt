package com.example.mycar.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.ui.fragment.CarDetailFragment
import com.example.mycar.ui.fragment.CarListFragment

private const val TAG = "CarWorker"
class CarWorker (ctx: Context, params: WorkerParameters) : Worker(ctx, params)  {
    override fun doWork(): Result {

        //to finish
        //makeStatusNotification("Cutting to do", appContext)

        // Arbitrary id number
        val notificationId = 17

        return try {

            val intent = Intent(applicationContext, CarDetailFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent: PendingIntent = PendingIntent
                .getActivity(applicationContext, 0, intent, 0)

            val carName = inputData.getString(nameKey)

            val builder = NotificationCompat.Builder(applicationContext, BaseApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_car)
                .setContentTitle("My Car!")
                .setContentText("It's time to give the model a coupon $carName")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(applicationContext)) {
                notify(notificationId, builder.build())
            }

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error notification")
            Result.failure()
        }
    }

    companion object {
        const val nameKey = "NAME"
    }
}