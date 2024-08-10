package com.example.notes.ui_layer

import com.example.notes.data_layer.Note

sealed interface NoteEvent {
    object SortNotes : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent
    data class SaveNote(val title: String, var disp: String): NoteEvent
}