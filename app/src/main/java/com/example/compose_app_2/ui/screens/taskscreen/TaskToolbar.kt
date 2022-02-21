package com.example.compose_app_2.ui.screens.taskscreen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.data.models.Task
import com.example.compose_app_2.ui.componente.DisplayAlertDialog
import com.example.compose_app_2.ui.theme.sec_back
import com.example.compose_app_2.ui.theme.sec_text
import com.example.compose_app_2.utils.Action
import com.example.compose_app_2.utils.ToolBarState

@Composable
fun TaskToolbar(
    navToListScreen: (Action) -> Unit,
    task: Task?
) {
    if (task == null)
        AddTaskToolbar(navToListScreen = navToListScreen)
    else
        EditTaskToolbar(
            task = task,
            navToListScreen = navToListScreen
        )
}

@Composable
fun AddTaskToolbar(
    navToListScreen: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = sec_back,
        title = {
            Text(text = "Add Task", color = sec_text)
        },
        navigationIcon = {
            IconButton(onClick = { navToListScreen(Action.NONE) }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = sec_text
                )
            }
        },
        actions = {
            IconButton(onClick = { navToListScreen(Action.ADD) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "",
                    tint = sec_text
                )
            }
        }
    )
}

@Composable
fun EditTaskToolbar(
    task: Task,
    navToListScreen: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = sec_back,
        title = {
            Text(
                text = task.title,
                color = sec_text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { navToListScreen(Action.NONE) }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = sec_text
                )
            }
        },
        actions = {
            ExsistingAction(
                task = task,
                navToListScreen = navToListScreen
            )
        }
    )
}

@Composable
fun ExsistingAction(
    task: Task,
    navToListScreen: (Action) -> Unit
)
{
    var openAlert by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        showAlert = openAlert,
        title ="Delte '${task.title}'?",
        text ="Are You sure you went to delte'${task.title}",
        closeDialog = { openAlert=false},
        confirmClick = {
            navToListScreen(Action.DELETE)
        }
    )
    IconButton(onClick = { navToListScreen(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "",
            tint = sec_text
        )
    }
    IconButton(onClick = {openAlert=true }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "",
            tint = sec_text
        )
    }
}


@Composable
@Preview
fun ADd() {
    EditTaskToolbar(Task(1, "mohamed", "", Priority.LOW), {})
}