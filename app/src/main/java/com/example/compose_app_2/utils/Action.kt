package com.example.compose_app_2.utils

enum class Action {
    ADD,
    DELETE,
    DELETE_ALL,
    NONE,
    UPDATE,
    UNDO
}

fun String?.toAction():Action
{
    return when
    {
        this == "ADD" ->{
            Action.ADD
        }
        this == "DELETE" ->{
            Action.DELETE
        }
        this == "DELETE_ALL" ->{
            Action.DELETE_ALL
        }
        this == "NONE" ->{
            Action.NONE
        }
        this == "UPDATE" ->{
            Action.UPDATE
        }
        this == "UNDO" ->{
            Action.UNDO
        }
        else -> {
            Action.NONE
        }
    }
}