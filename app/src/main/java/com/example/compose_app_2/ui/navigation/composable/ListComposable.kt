package com.example.compose_app_2.ui.navigation.composable

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.compose_app_2.ui.screens.ListScreen
import com.example.compose_app_2.utils.Constants
import com.example.compose_app_2.utils.toAction
import com.example.compose_app_2.viewmodels.SharedViewModel

@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen:(Int)-> Unit,
    sharedViewModel: SharedViewModel
)
{
    composable(
        route =Constants.LIST_SCREEN,
        arguments = arrayListOf(
            navArgument(name = Constants.LIST_ARGUMENT_ACTION){
                type= NavType.StringType
            }
        )
    ){
        val action=it.arguments?.getString(Constants.LIST_ARGUMENT_ACTION).toAction()
        LaunchedEffect(key1 = action){
            sharedViewModel.action.value=action
        }
        ListScreen(navigateToTaskScreen,sharedViewModel)
    }
}