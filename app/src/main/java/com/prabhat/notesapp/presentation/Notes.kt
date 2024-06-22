package com.prabhat.notesapp.presentation

import kotlinx.serialization.Serializable

@Serializable
data class Notes(
    val title:String,
    val description:String,
    val date:String
)
