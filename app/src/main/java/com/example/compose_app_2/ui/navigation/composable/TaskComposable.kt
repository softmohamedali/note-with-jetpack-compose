package com.example.compose_app_2.ui.navigation.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.compose_app_2.ui.screens.TaskScreen
import com.example.compose_app_2.utils.Action
import com.example.compose_app_2.utils.Constants
import com.example.compose_app_2.utils.ToolBarState
import com.example.compose_app_2.viewmodels.SharedViewModel

fun NavGraphBuilder.taskComposable(
    navigateToListScreen:(Action)-> Unit,
    sharedViewModel: SharedViewModel
)
{
    composable(
        route = Constants.TASK_SCREEN,
        arguments = arrayListOf(
            navArgument(name = Constants.TASK_ARGUMENT_ID){
                type= NavType.IntType
            }
        )
    ){
        val id=it.arguments!!.getInt(Constants.TASK_ARGUMENT_ID)
        sharedViewModel.getTaskById(id)
        val task by sharedViewModel.taskById.collectAsState()

        LaunchedEffect(key1 = task){
            if (task!=null||id==-1) {
                sharedViewModel.updatetask(task)
            }
        }

        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            task = task,
            sharedViewModel = sharedViewModel
        )
    }
}