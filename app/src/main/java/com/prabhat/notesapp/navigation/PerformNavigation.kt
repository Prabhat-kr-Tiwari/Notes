package com.prabhat.notesapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.prabhat.notesapp.domain.model.Note
import com.prabhat.notesapp.presentation.screens.AddNoteScreen

import com.prabhat.notesapp.presentation.screens.NotesScreen
import kotlinx.serialization.Serializable


@Composable
fun PerformNavigation() {


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {
        composable(Screen.NotesScreen.route){

         NotesScreen(navHostController = navController)
        }
        composable(Screen.AddNotesScreen.route){

            AddNoteScreen(navHostController = navController)
        }
        composable<addNoteScreen> {
            AddNoteScreen(navHostController = navController)
        }
        composable<Note> {

//            val arg=it.toRoute<Notes>()
            val arg=it.toRoute<Note>()
            Log.d("PRABHAT", "PerformNavigation: ${arg.toString()}")
            AddNoteScreen(notes = arg,navHostController = navController)
        }



    }
}
@Serializable
object addNoteScreen
