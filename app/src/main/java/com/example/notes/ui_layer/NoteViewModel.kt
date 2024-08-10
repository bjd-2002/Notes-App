package com.example.notes.ui_layer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data_layer.Note
import com.example.notes.data_layer.NoteDatabese
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NoteViewModel @Inject constructor(noteDatabese: NoteDatabese) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    val dao = noteDatabese.noteDao
    private val isSortedByDateAdded = MutableStateFlow(true)
    @OptIn(ExperimentalCoroutinesApi::class)
    private var notes = isSortedByDateAdded.flatMapLatest {
        if (it) {
            dao.getAllOrderedDateAdded()
        } else {
            dao.getAllOrderedTitle()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(), emptyList()
    )
    val _state = MutableStateFlow(NoteState())
    val state = combine(_state,isSortedByDateAdded,notes,searchText) { state,isSortedByDateAdded,notes,searchText->
        if (searchText.isBlank()) {
            state.copy(
                notes = notes
            )
        }
        else {
            state.copy(
                notes = notes.filter { it.doesMatchSearchQuery(searchText) }
            )
        }

    }.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NoteState()
    )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onEvent(event: NoteEvent) {
        when(event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.delete(event.note)
                }
            }
            is NoteEvent.SaveNote -> {

                    val note = Note(
                        title = state.value.title.value,
                        disp = state.value.disp.value,
                        dateAdded = System.currentTimeMillis()
                    )
                    viewModelScope.launch {
                        dao.upsert(note)
                        _state.update {
                            it.copy(title = mutableStateOf(""), disp = mutableStateOf(""))
                        }
                    }

            }
            is NoteEvent.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
        }
    }
}