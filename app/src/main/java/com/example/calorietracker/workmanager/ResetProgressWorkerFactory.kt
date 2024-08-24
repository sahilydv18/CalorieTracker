package com.example.calorietracker.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.calorietracker.database.MealDao
import com.example.calorietracker.datastore.repo.PreferencesRepo
import javax.inject.Inject
import javax.inject.Singleton

// custom worker factory, so that dagger hilt can instantiate the worker
// as dagger hilt don't know how to implement predefined WorkerFactory for custom worker made by us
@Singleton
class ResetProgressWorkerFactory @Inject constructor(
    private val mealDao: MealDao,
    private val preferencesRepo: PreferencesRepo
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return when (workerClassName) {
            ResetProgressWorker::class.java.name -> {
                ResetProgressWorker(appContext, workerParameters, mealDao, preferencesRepo)
            }
            else -> throw IllegalArgumentException("Unknown worker class name: $workerClassName")
        }
    }
}