package com.example.mycar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mycar.workers.CarWorker
import java.util.concurrent.TimeUnit

class CarNotificationViewModel(application: Application): ViewModel() {

    private val workManager = WorkManager.getInstance(application)

    internal fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        carName: String
    ) {

        val data = Data.Builder().putString(CarWorker.nameKey, carName).build()

        val carReminderBuilder = OneTimeWorkRequestBuilder<CarWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(data)
            .build()

        workManager.enqueueUniqueWork(carName, ExistingWorkPolicy.REPLACE, carReminderBuilder)
    }
}

class CarNotificationViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarNotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarNotificationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}