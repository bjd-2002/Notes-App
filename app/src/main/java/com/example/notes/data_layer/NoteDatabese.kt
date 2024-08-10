package com.example.notes.data_layer

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(exportSchema = true, entities = arrayOf(Note::class), version = 1)
abstract class NoteDatabese : RoomDatabase() {
    abstract val noteDao : NoteDao
}