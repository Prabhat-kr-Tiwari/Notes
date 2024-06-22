package com.prabhat.notesapp.mappers


import com.prabhat.notesapp.data.model.NotesDTO
import com.prabhat.notesapp.domain.model.Note

fun List<NotesDTO>.toDomain():List<Note>{

    return map {
        Note(
            id = it.id?:0,
            title = it.title?:"",
            description = it.description?:"",
            date = it.date?:""
        )
    }

}
fun Note.toDTO():NotesDTO{
    return NotesDTO(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date
    )
}
fun NotesDTO.toNote():Note{
    return Note(
        id=this.id,
        title=this.title?:"",
        description = this.description?:"",
        date=this.description?:""
        )
}