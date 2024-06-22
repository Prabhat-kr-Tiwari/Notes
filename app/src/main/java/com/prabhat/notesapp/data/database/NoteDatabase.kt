package com.prabhat.notesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prabhat.notesapp.data.model.NotesDTO


@Database(entities = [NotesDTO::class], version = 1, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {



    abstract fun notesDao():NoteDao

}