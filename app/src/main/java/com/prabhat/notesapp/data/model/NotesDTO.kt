package com.prabhat.notesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notesTable")
data class NotesDTO(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String?,
    val description:String?,
    val date:String?
)
