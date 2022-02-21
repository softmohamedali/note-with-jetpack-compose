package com.example.compose_app_2.data.models

import androidx.compose.ui.graphics.Color
import com.example.compose_app_2.ui.theme.high
import com.example.compose_app_2.ui.theme.low
import com.example.compose_app_2.ui.theme.mediume
import com.example.compose_app_2.ui.theme.none

enum class Priority(var color:Color) {
    LOW(low),
    MEDIUME(mediume),
    HIGH(high),
    NONE(none),
}