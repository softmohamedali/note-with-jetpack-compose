package com.example.compose_app_2.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose_app_2.ui.navigation.composable.listComposable
import com.example.compose_app_2.ui.navigation.composable.taskComposable
import com.example.compose_app_2.utils.Constants
import com.example.compose_app_2.viewmodels.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun SetUpNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screens= remember(navController){
        Screens(navController = navController)
    }

    NavHost(navController = navController,startDestination = Constants.LIST_SCREEN){
        listComposable(
            navigateToTaskScreen = screens.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screens.list,
            sharedViewModel = sharedViewModel
        )
    }


}