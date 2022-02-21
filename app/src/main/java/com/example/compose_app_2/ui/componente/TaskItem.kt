package com.example.compose_app_2.ui.componente

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.data.models.Task

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    task: Task,
    navigateToTaskScreen:(Int)-> Unit
){
    Surface(
        onClick = {
        navigateToTaskScreen(task.id)
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
                ){
            Row(
            ){
                Text(
                    text = task.title!!,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(8f)
                )
                Box (
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.TopEnd
                ){
                    Canvas(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)){
                        drawCircle(color = task.priority.color)
                    }
                }
            }
            Text(
                text = task.note!!,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun fgsdf(){
    TaskItem(task = Task(
        title = "asvs",
        note = "zzxfvzxvc",
        priority =Priority.LOW
    ),{})
}