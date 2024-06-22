package com.prabhat.notesapp.presentation

import com.prabhat.notesapp.domain.model.Note


data class NoteStateHolder(
    val isLoading:Boolean=false,
    val data:List<Note>?=null,
    val error:String=""

)
