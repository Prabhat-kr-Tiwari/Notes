package com.prabhat.notesapp.navigation

import kotlinx.serialization.Serializable

sealed class Screen(val route:String) {


    object NotesScreen:Screen("NotesScreen")
    object AddNotesScreen:Screen("AddNotesScreen")
    object AddNotesScreenWithData:Screen("AddNotesScreen")


}