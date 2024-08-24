package com.example.calorietracker

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.calorietracker.workmanager.ResetProgressWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CalorieTrackerApplication: Application() {
    // custom worker factory
    @Inject lateinit var workerFactory: WorkerFactory

    private lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()

        // initializing WorkManager with custom WorkerFactory
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )

        workManager = WorkManager.getInstance(applicationContext)

        // setting up work
        val resetWork = PeriodicWorkRequestBuilder<ResetProgressWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()

        // adding unique work to the work manager
        workManager.enqueueUniquePeriodicWork(
            "Reset Progress",
            ExistingPeriodicWorkPolicy.UPDATE,
            resetWork
        )
    }

    // function for calculating the left time for midnight, so to set delay for the worker
    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            timeInMillis = now.timeInMillis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (now.after(this)) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        return midnight.timeInMillis - now.timeInMillis
    }
}