package com.prabhat.notesapp.data.di

import android.app.Application
import androidx.room.Room
import com.prabhat.notesapp.data.database.NoteDao
import com.prabhat.notesapp.data.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    fun provideDatabase(application: Application): NoteDatabase =
        Room.databaseBuilder(application, NoteDatabase::class.java, "NotesDatabase")
            .fallbackToDestructiveMigrationFrom().build()

    @Provides
    @Singleton
    fun providesDao(db:NoteDatabase):NoteDao=db.notesDao()


}