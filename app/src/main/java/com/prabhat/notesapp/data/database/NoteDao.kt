package com.prabhat.notesapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.prabhat.notesapp.data.model.NotesDTO


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notesDTO: NotesDTO)


    @Query("SELECT * FROM notesTable")
    suspend fun getAllNotes():List<NotesDTO>

    @Delete
    suspend fun deleteNote(notesDTO: NotesDTO)

    @Update
    suspend fun updateNote(notesDTO: NotesDTO)

    // selecting one note at a time
    @Query("SELECT * FROM notesTable WHERE id LIKE :id")
    fun getNote(id : Int) : NotesDTO
}