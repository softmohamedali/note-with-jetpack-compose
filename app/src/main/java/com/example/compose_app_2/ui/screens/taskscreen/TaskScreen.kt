package com.example.compose_app_2.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.data.models.Task
import com.example.compose_app_2.ui.screens.taskscreen.TaskScreenContent
import com.example.compose_app_2.ui.screens.taskscreen.TaskToolbar
import com.example.compose_app_2.utils.Action
import com.example.compose_app_2.viewmodels.SharedViewModel

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    task: Task?,
    sharedViewModel: SharedViewModel
) {
    var title by sharedViewModel.title
    var description by sharedViewModel.description
    var priority by sharedViewModel.priority
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TaskToolbar(
                {
                    if (it == Action.NONE) {
                        navigateToListScreen(it)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(it)
                        } else {
                            showToast(context)
                        }
                    }
                },
                task
            )
        },
        content = {
            TaskScreenContent(
                title = title,
                onTitleChange = { title = it },
                desiption = description,
                onDescriptionChange = { description = it },
                priority = priority,
                onPrioritySelected = { priority = it }
            )
        },
    )
}

fun showToast(context: Context) {
    Toast.makeText(context, "Empty Fields !", Toast.LENGTH_SHORT).show()
}
