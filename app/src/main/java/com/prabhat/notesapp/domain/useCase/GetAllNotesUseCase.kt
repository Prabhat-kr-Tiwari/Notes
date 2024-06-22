package com.prabhat.notesapp.domain.useCase

import com.prabhat.notesapp.domain.model.Note
import com.prabhat.notesapp.domain.repository.NoteRepository
import com.prabhat.notesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val noteRepository: NoteRepository) {


    operator fun invoke(): Flow<Resource<List<Note>>> = flow {
        emit(Resource.Loading(""))
        try {

            emit(Resource.Success(noteRepository.getAllNotes()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }


    }
}