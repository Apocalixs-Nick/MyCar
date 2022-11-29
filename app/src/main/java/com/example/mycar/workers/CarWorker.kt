package com.example.mycar.workers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mycar.BaseApplication
import com.example.mycar.MainActivity
import com.example.mycar.R
import com.example.mycar.ui.fragment.CarDetailFragment
import com.example.mycar.ui.fragment.CarListFragment

private const val TAG = "CarWorker"

class CarWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun doWork(): Result {

        //to finish
        //makeStatusNotification("Cutting to do", appContext)

        // Arbitrary id number
        val notificationId = 17


        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(applicationContext, 0, intent, FLAG_IMMUTABLE)

        val carName = inputData.getString(nameKey)

        val builder = NotificationCompat.Builder(applicationContext, BaseApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_car)
            .setContentTitle("My Car!")
            .setContentText("It's time to car service to the model $carName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, builder.build())
        }

        return Result.success()
    }

    companion object {
        const val nameKey = "NAME"
    }
}