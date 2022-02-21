package com.example.compose_app_2.ui.componente

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.ui.theme.main_back
import com.example.compose_app_2.ui.theme.none
import com.example.compose_app_2.ui.theme.sec_back
import com.example.compose_app_2.ui.theme.sec_text

@Composable
fun ProrityDropDownItem(
    priority: Priority,
    OnItemSelected: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val iconArrowState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(sec_back)
            .clickable {
                expanded = true
            }
            .border(
                width = 1.dp,
                color = none.copy(ContentAlpha.medium),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Canvas(
            modifier = Modifier
                .size(16.dp)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.weight(8f),
            text = priority.name,
            style = TextStyle(fontSize = 16.sp)
        )
        IconButton(onClick = { expanded = true }, modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                modifier = Modifier.rotate(iconArrowState),
                tint = sec_text
            )
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.94f)
                .background(main_back),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                OnItemSelected(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                OnItemSelected(Priority.MEDIUME)
            }) {
                PriorityItem(priority = Priority.MEDIUME)

            }
            DropdownMenuItem(onClick = {
                expanded = false
                OnItemSelected(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
        }

    }
}

@Composable
@Preview
fun AA() {
    ProrityDropDownItem(Priority.LOW, {})
}