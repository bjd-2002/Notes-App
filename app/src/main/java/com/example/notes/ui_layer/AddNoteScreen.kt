package com.example.notes.ui_layer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AddNoteScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: NoteState,
    onEvent: (NoteEvent) -> Unit
) {
    Scaffold(
        topBar = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    NoteEvent.SaveNote(
                        title = state.title.value,
                        disp = state.disp.value
                    )
                )
                navController.popBackStack()
            }
            ) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 10.dp)
        ) {
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = state.title.value, onValueChange = {
                state.title.value = it
            })
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = state.disp.value, onValueChange = {
                state.disp.value = it
            })
        }
    }
}