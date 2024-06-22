package com.prabhat.notesapp.domain.di

import com.prabhat.notesapp.data.database.NoteDao
import com.prabhat.notesapp.data.repository.NoteRepositoryImpl
import com.prabhat.notesapp.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetNotesRepo(noteDao: NoteDao):NoteRepository{

        return NoteRepositoryImpl(noteDao = noteDao)
    }

}