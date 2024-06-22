package com.prabhat.notesapp.presentation.screens



import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prabhat.notesapp.R
import com.prabhat.notesapp.domain.model.Note
import com.prabhat.notesapp.navigation.Screen

import com.prabhat.notesapp.presentation.viewModel.NotesViewModel
import com.prabhat.notesapp.windowSize.WindowType
import com.prabhat.notesapp.windowSize.rememberWindowSize


@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val allnotes = notesViewModel.allNotes.value
    val insertNote = notesViewModel.insertNote.value
    val deletetNote = notesViewModel.deleteNote.value
    val filtertNotes = notesViewModel.filteredNotes.value

    if (allnotes.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {


            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        }
    }
    if (allnotes.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize()) {


            Text(text = allnotes.error, modifier = Modifier.align(Alignment.Center))

        }
    }


    LaunchedEffect(insertNote) {
        notesViewModel.getAllNotes()
    }

  /*  if (deletetNote.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {


            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        }
    }
    if (deletetNote.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize()) {


            Text(text = deletetNote.error, modifier = Modifier.align(Alignment.Center))

        }
    }*/
    LaunchedEffect(deletetNote) {
        notesViewModel.getAllNotes()

    }
//    val listState = rememberLazyListState()
    val listState = rememberLazyGridState()
    // The FAB is initially expanded. Once the first visible item is past the first item we
    // collapse the FAB. We use a remembered derived state to minimize unnecessary compositions.
    val expandedFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navHostController.navigate(Screen.AddNotesScreen.route) },
                expanded = expandedFab,
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        "Localized Description",
                        tint = colorResource(id = R.color.orange)
                    )
                },
                text = {
                    Text(text = "Add Notes", color = colorResource(id = R.color.orange))
                },
                containerColor = colorResource(id = R.color.gray_100)
            )
        },
        // isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.padding(bottom = 20.dp)
    ) { it ->
        val h = it

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(bottom = 20.dp)
        ) {


            var query by remember { mutableStateOf("") }


            TextField(
                value = query,
                onValueChange = {
                    query = it
                    notesViewModel.filterNotes(query)

                },
                label = {
                    Text("")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(10.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "",

                        tint = colorResource(id = R.color.orange)
                    )
                },
                trailingIcon = {
                    notesViewModel.filterNotes(query = query)

                    if (query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "",
                            tint = colorResource(id = R.color.orange),
                            modifier = Modifier.clickable {
                                query = ""
                                notesViewModel.filterNotes(query = query)
                            }
                        )
                    }

                },

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


            filtertNotes.data?.let { it1 ->
                NotesSection(
                    navHostController = navHostController,
                    lazyListState = listState,
                    notes = it1,
                    onDelete = {note->
                        Log.d("PRABHAT", "NotesScreen: $note")
                        notesViewModel.deleteNote(note = note)
                        if (deletetNote.data=="Note Deleted Successfully"){
                            notesViewModel.getAllNotes()
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}





//@Preview
@Composable
fun NotesSection(
    navHostController: NavHostController,
    lazyListState: LazyGridState,
    notes: List<Note>,
    onDelete: (Note) -> Unit
) {


    val windowSize= rememberWindowSize()
    when(windowSize.width){
        WindowType.Compact->{
            LazyVerticalGrid(columns = GridCells.Fixed(2), state = lazyListState) {


                items(notes.size) { index ->
                    EachItem(
                        navHostController = navHostController,
                        note = notes[index],
                        isLastItem = index == notes.size - 1,
                        onDelete = onDelete
                    )
                    if (index == notes.size - 1) Spacer(modifier = Modifier.height(24.dp))

                }


            }
        }else->{

        LazyVerticalGrid(columns = GridCells.Fixed(4), state = lazyListState) {


            items(notes.size) { index ->
                EachItem(
                    navHostController = navHostController,
                    note = notes[index],
                    isLastItem = index == notes.size - 1,
                    onDelete = onDelete
                )
                if (index == notes.size - 1) Spacer(modifier = Modifier.height(24.dp))

            }


        }
    }
    }

 /*   LazyVerticalGrid(columns = GridCells.Fixed(2), state = lazyListState) {


        items(notes.size) { index ->
            EachItem(
                navHostController = navHostController,
                note = notes[index],
                isLastItem = index == notes.size - 1,
                onDelete = onDelete
            )
            if (index == notes.size - 1) Spacer(modifier = Modifier.height(24.dp))

        }


    }*/

}


@Composable
fun EachItem(navHostController: NavHostController, note: Note, isLastItem: Boolean,onDelete:(Note)->Unit) {


    val lastItemPaddingEnd = if (isLastItem) 24.dp else 0.dp  // Change the padding value as needed

    OutlinedCard(
        modifier = Modifier.padding(
            start = 20.dp,
            end = 20.dp,
            top = 20.dp,
            bottom = lastItemPaddingEnd
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        ),
        onClick = {

            navHostController.navigate(Note(note.id,note.title, note.description, note.date))
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier =
            Modifier
                .background(color = colorResource(id = R.color.gray_100))
                .width(250.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(30.dp))
                .padding(bottom = 20.dp)


        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = note.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,

                        fontSize = if (note.title.length > 5) 20.sp else 24.sp

                    ),
                    modifier = Modifier.width(80.dp)
                )

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = colorResource(id = R.color.orange),
                    modifier = Modifier.clickable {

                        //delete note
                        onDelete(note)


                    }
                )
            }


            Text(
                text = note.description,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = note.date,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(start = 10.dp),
                color = colorResource(
                    id = R.color.blue_gray_200
                )
            )


        }
    }


}

