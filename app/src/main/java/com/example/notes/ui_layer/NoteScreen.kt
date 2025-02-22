package com.example.notes.ui_layer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    viewModel: NoteViewModel
) {
    Scaffold(
        topBar = {
            Column {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(
                        text = "Notes App",
                        modifier = Modifier.weight(1f),
                        fontSize = 17.sp
                    )
                    IconButton(onClick = { onEvent(NoteEvent.SortNotes) }) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.Sort, contentDescription = null)
                    }
                }
                val searchText by viewModel.searchText.collectAsState()
                TextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Search") }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_note_screen") }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = it
        ) {
            items(state.notes) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = it.title)
                        Text(text = it.disp)
                    }
                    IconButton(onClick = {
                        onEvent(NoteEvent.DeleteNote(it))
                    }) {
                        Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
                    }
                }
            }
        }
    }
}