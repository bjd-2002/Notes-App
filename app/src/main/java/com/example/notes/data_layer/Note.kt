package com.example.notes.data_layer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    var title: String,
    var disp: String,
    var dateAdded: Long
){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            title,
            disp
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
