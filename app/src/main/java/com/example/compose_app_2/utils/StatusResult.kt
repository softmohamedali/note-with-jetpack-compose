package com.example.compose_app_2.utils

sealed class StatusResult<out T>{
    object OnInit:StatusResult<Nothing>()
    object OnLoading:StatusResult<Nothing>()
    data class OnSuccess<T>(var data:T):StatusResult<T>()
    data class OnError<T>(var error:Throwable):StatusResult<T>()


}
