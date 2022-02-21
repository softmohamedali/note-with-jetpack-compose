package com.example.compose_app_2.ui.navigation

import androidx.navigation.NavController
import com.example.compose_app_2.utils.Action
import com.example.compose_app_2.utils.Constants

class Screens(navController: NavController) {
    var list:(Action) -> Unit ={
        navController.navigate("list/${it.name}"){
            popUpTo(Constants.LIST_SCREEN){inclusive=true}
        }
    }
    var task:(Int) -> Unit={
        navController.navigate("task/${it}")
    }
}