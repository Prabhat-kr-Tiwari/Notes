package com.prabhat.notesapp.domain.repository

import com.prabhat.notesapp.data.model.NotesDTO
import com.prabhat.notesapp.domain.model.Note

interface NoteRepository {


    suspend fun insertNote(note: Note)
    suspend fun getAllNotes(): List<Note>
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun getNote(id: Int): Note


}