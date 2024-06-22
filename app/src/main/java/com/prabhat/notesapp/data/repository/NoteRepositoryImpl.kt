package com.prabhat.notesapp.data.repository

import com.prabhat.notesapp.data.database.NoteDao
import com.prabhat.notesapp.domain.model.Note
import com.prabhat.notesapp.domain.repository.NoteRepository
import com.prabhat.notesapp.mappers.toDTO
import com.prabhat.notesapp.mappers.toDomain
import com.prabhat.notesapp.mappers.toNote
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) {
        noteDao.insert(notesDTO = note.toDTO())
    }

    override suspend fun getAllNotes(): List<Note> {
       val response=noteDao.getAllNotes()
        return response.toDomain()
    }

    override suspend fun deleteNote(note: Note) {
       noteDao.deleteNote(notesDTO =note.toDTO() )
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(notesDTO = note.toDTO())
    }

    override suspend fun getNote(id: Int): Note {
        val notesDTO= noteDao.getNote(id = id)
        return notesDTO.toNote()
    }

}