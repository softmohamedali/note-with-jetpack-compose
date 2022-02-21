package com.example.compose_app_2.ui.componente

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.ui.theme.ball_size
import com.example.compose_app_2.ui.theme.normal_padding
import com.example.compose_app_2.ui.theme.small_padding


@Composable
fun PriorityItem(
    priority: Priority
){
    Row {
        Canvas(
            modifier = Modifier.size(ball_size)
        ){
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = small_padding)
        )
    }
}

@Composable
@Preview
fun ff()
{
    PriorityItem(priority = Priority.LOW)
}