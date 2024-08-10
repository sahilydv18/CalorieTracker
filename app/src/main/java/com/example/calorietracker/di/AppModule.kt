package com.example.calorietracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.calorietracker.data.PreferencesRepo
import com.example.calorietracker.data.PreferencesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// using dagger hilt to provide dependency injection
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("basicInfo")
            }
        )
    }

    @Provides
    @Singleton
    fun providesPreferencesRepo(dataStore: DataStore<Preferences>): PreferencesRepo {
        return PreferencesRepoImpl(dataStore)
    }
}