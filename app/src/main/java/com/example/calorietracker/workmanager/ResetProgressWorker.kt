package com.example.calorietracker.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.calorietracker.database.MealDao
import com.example.calorietracker.datastore.repo.PreferencesRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// worker for resetting the progress
@HiltWorker
class ResetProgressWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val mealDao: MealDao,
    private val preferencesRepo: PreferencesRepo
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                preferencesRepo.updateCompletedCalorie(0)
                preferencesRepo.updateCompletedProtein(0)
                preferencesRepo.updateCompletedCarbs(0)
                preferencesRepo.updateCompletedFat(0)

                mealDao.resetMealCompletionStatus()

                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}
