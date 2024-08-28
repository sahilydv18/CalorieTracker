package com.example.calorietracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.work.WorkerFactory
import com.example.calorietracker.BuildConfig
import com.example.calorietracker.database.AppDatabase
import com.example.calorietracker.database.MealDao
import com.example.calorietracker.database.repo.MealRepo
import com.example.calorietracker.database.repo.MealRepoImpl
import com.example.calorietracker.datastore.repo.PreferencesRepo
import com.example.calorietracker.datastore.repo.PreferencesRepoImpl
import com.example.calorietracker.remote.IngredientApi
import com.example.calorietracker.remote.IngredientInterceptor
import com.example.calorietracker.remote.repo.IngredientApiRepo
import com.example.calorietracker.remote.repo.IngredientApiRepoImpl
import com.example.calorietracker.workmanager.ResetProgressWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.calorieninjas.com/"

// using dagger hilt to provide dependency injection
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // function for providing data store preferences
    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("basicInfo")
            }
        )
    }

    // function for providing preferences repo
    @Provides
    @Singleton
    fun providesPreferencesRepo(dataStore: DataStore<Preferences>): PreferencesRepo {
        return PreferencesRepoImpl(dataStore)
    }

    // function for providing app database
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "meal_database"
        ).build()
    }

    // function for providing meal Dao
    @Provides
    @Singleton
    fun providesMealDao(appDatabase: AppDatabase): MealDao {
        return appDatabase.mealDao()
    }

    // function for providing meal repo
    @Provides
    @Singleton
    fun providesMealRepo(mealDao: MealDao): MealRepo {
        return MealRepoImpl(mealDao)
    }

    // function for  providing custom WorkerFactory
    @Provides
    fun provideWorkerFactory(
        mealDao: MealDao,
        preferencesRepo: PreferencesRepo
    ): WorkerFactory {
        return ResetProgressWorkerFactory(mealDao,preferencesRepo)
    }

    // function for providing IngredientApi
    @Provides
    @Singleton
    fun providesIngredientApi(): IngredientApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(IngredientInterceptor(BuildConfig.API_KEY))
            .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IngredientApi :: class.java)
    }

    // function for providing ingredientApiRepo
    @Provides
    @Singleton
    fun providesIngredientRepo(ingredientApi: IngredientApi): IngredientApiRepo{
        return IngredientApiRepoImpl(ingredientApi)
    }
}