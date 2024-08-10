package com.example.notes.di

import android.app.Application
import androidx.room.Room
import com.example.notes.data_layer.NoteDatabese
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providerDatabase(application: Application): NoteDatabese{
        return Room.databaseBuilder(
            context = application,
            NoteDatabese::class.java,
            "note_database.db"
        ).build()
    }
}