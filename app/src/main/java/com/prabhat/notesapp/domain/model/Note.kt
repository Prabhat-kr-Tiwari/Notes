package com.prabhat.notesapp.domain.model

import kotlinx.serialization.Serializable

/*
data class Note (
    val id:Int,
    val title:String,
    val description:String,
    val date:String
)*/
@Serializable
data class Note (

    val id:Int=0,
    val title:String,
    val description:String,
    val date:String="2024-09-08"
)
