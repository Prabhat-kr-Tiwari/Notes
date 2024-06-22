package com.prabhat.notesapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prabhat.notesapp.R
import com.prabhat.notesapp.domain.model.Note
import com.prabhat.notesapp.presentation.viewModel.NotesViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun AddNoteScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    notes: Note? = null,
    navHostController: NavHostController
) {

    var title by remember { mutableStateOf(notes?.title ?: "") }
    var description by remember { mutableStateOf(notes?.description ?: "") }
    val noteId = notes?.id

    val date = getCurrentDate()
    val insertNoteState = notesViewModel.insertNote.value
    val updateNoteState = notesViewModel.updateNote.value
    if (insertNoteState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {


            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        }
    }
    if (insertNoteState.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize()) {


            Text(text = insertNoteState.error, modifier = Modifier.align(Alignment.Center))

        }
    }

    Scaffold(floatingActionButton = {


        LargeFloatingActionButton(


            onClick = {

                if (noteId != null) {

                    notesViewModel.updateNote(
                        Note(
                            id = noteId,
                            title = title,
                            description = description,
                            date = date
                        )
                    )
                    updateNoteState.data.let {
                        navHostController.popBackStack()
                    }

                } else {

                    notesViewModel.insertNote(
                        Note(
                            id = 0,
                            title = title,
                            description = description,
                            date = date
                        )
                    )


                    insertNoteState.data.let {
                        navHostController.popBackStack()

                    }
                }


            },
            containerColor = colorResource(id = R.color.gray_100)


        ) {

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
                tint = colorResource(id = R.color.orange), modifier = Modifier.size(50.dp)
            )

        }


    }) { it ->
        val h = it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {

            Text(
                text = "Title",
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier.padding(top = 50.dp, start = 24.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = title, onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults
                    .colors(

                        unfocusedContainerColor = colorResource(id = R.color.gray_100),
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent

                    ),
                singleLine = true

            )


            Text(
                text = "Description",
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier.padding(top = 50.dp, start = 24.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = description, onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults
                    .colors(

                        unfocusedContainerColor = colorResource(id = R.color.gray_100),
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent

                    ),
                singleLine = true

            )
        }

    }


}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}