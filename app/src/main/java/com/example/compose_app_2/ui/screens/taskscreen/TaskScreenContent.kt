package com.example.compose_app_2.ui.screens.taskscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.ui.componente.ProrityDropDownItem

@Composable
fun TaskScreenContent(
    title:String,
    onTitleChange:(String)-> Unit,
    priority: Priority,
    onPrioritySelected:(Priority)->Unit,
    desiption:String,
    onDescriptionChange:(String)->Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =title,
            onValueChange ={onTitleChange(it)},
            singleLine = true,
            label = { Text(text = "title")}
        )
        Spacer(modifier = Modifier.height(12.dp))
        ProrityDropDownItem(
            priority = priority,
            OnItemSelected = {onPrioritySelected(it)}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value =desiption,
            onValueChange ={onDescriptionChange(it)},
            label = { Text(text = "your discription here")},
            textStyle = MaterialTheme.typography.body1
        )
    }

}