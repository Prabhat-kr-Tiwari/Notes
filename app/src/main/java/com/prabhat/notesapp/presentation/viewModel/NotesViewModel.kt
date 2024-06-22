package com.prabhat.notesapp.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.notesapp.domain.model.Note
import com.prabhat.notesapp.domain.useCase.DeleteNoteUseCase
import com.prabhat.notesapp.domain.useCase.GetAllNotesUseCase
import com.prabhat.notesapp.domain.useCase.GetNoteUseCase
import com.prabhat.notesapp.domain.useCase.InsertNoteUseCase
import com.prabhat.notesapp.domain.useCase.UpdateNoteUseCase
import com.prabhat.notesapp.presentation.NoteStateHolder
import com.prabhat.notesapp.presentation.NotesStateHolder
import com.prabhat.notesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {
    val allNotes = mutableStateOf(NoteStateHolder())
    val insertNote = mutableStateOf(NotesStateHolder())
    val deleteNote = mutableStateOf(NotesStateHolder())
    val updateNote = mutableStateOf(NotesStateHolder())
    val filteredNotes = mutableStateOf(NoteStateHolder())
    init {
        getAllNotes()
//        insertNote()
    }
    fun getAllNotes() {
        Log.d("PRABHAT", "getAllNotes: ")
        getAllNotesUseCase().onEach {

            when (it) {
                is Resource.Loading -> {
                    allNotes.value = NoteStateHolder(isLoading = true)
                }

                is Resource.Error -> {
                    allNotes.value = NoteStateHolder(error = it.message.toString())
                }

                is Resource.Success -> {
                    allNotes.value = NoteStateHolder(data = it.data)
                }
            }


        }.launchIn(viewModelScope)

    }
    fun insertNote(note: Note){
        insertNoteUseCase(note = note).onEach {
            when (it) {
                is Resource.Loading -> {
                    insertNote.value = NotesStateHolder(isLoading = true)
                }
                is Resource.Error -> {
                    insertNote.value = NotesStateHolder(error = it.message.toString())
                }
                is Resource.Success -> {
                    insertNote.value = NotesStateHolder(data = "Added Successfully")

                    getAllNotes()
                }
            }
        }.launchIn(viewModelScope)
    }
    fun deleteNote(note: Note){
        deleteNoteUseCase(note = note).onEach {
            when (it) {
                is Resource.Loading -> {
                    deleteNote.value = NotesStateHolder(isLoading = true)

                }
                is Resource.Error -> {
                    deleteNote.value = NotesStateHolder(error = it.message.toString())

                }
                is Resource.Success -> {

                    deleteNote.value = NotesStateHolder(data = "Note Deleted Successfully")


                }
            }
        }.launchIn(viewModelScope)
    }
    fun updateNote(note: Note){
        updateNoteUseCase(note = note).onEach {
            when (it) {
                is Resource.Loading -> {
                    updateNote.value = NotesStateHolder(isLoading = true)
                    Log.d("NotesViewModel", "update: loading ")
                }
                is Resource.Error -> {
                    updateNote.value = NotesStateHolder(error = it.message.toString())
                    Log.e("NotesViewModel", "Error updating note: ${it.message}")
                }
                is Resource.Success -> {
                    Log.d("NotesViewModel", "deleteNote:Success ")
                    updateNote.value = NotesStateHolder(data = "Note Updated Successfully")


                }
            }
        }.launchIn(viewModelScope)
    }
    fun filterNotes(query: String) {
        val allNotesList = allNotes.value.data ?: emptyList()
        val filteredList = if (query.isBlank()) {
            allNotesList

        } else {
            allNotesList.filter { note ->
                note.title.contains(query, ignoreCase = true) || note.description.contains(query, ignoreCase = true)
            }
        }
        filteredNotes.value = NoteStateHolder(data = filteredList)
    }


}