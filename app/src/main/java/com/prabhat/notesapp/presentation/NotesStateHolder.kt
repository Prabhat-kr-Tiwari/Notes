package com.prabhat.notesapp.presentation

data class NotesStateHolder(
    val isLoading: Boolean = false,
    val data: String? = "",
    val error: String = ""
)
