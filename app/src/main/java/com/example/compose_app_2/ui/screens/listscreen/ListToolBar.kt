package com.example.compose_app_2.ui.screens.listscreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.example.compose_app_2.R
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.ui.componente.DisplayAlertDialog
import com.example.compose_app_2.ui.componente.PriorityItem
import com.example.compose_app_2.ui.theme.*
import com.example.compose_app_2.utils.Action
import com.example.compose_app_2.utils.ToolBarState
import com.example.compose_app_2.viewmodels.SharedViewModel
import kotlin.math.exp

@Composable
fun ToolBarListScreen(
    sharedViewModel: SharedViewModel,
    toolBarState: ToolBarState,
    toolBarSearchValue:String
) {

    var openAlert by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        showAlert =openAlert,
        title ="Delte All Tasks ?",
        text ="Are you sure you want to Delte All Tasks",
        closeDialog = {openAlert=false},
        confirmClick = {
            sharedViewModel.action.value=Action.DELETE_ALL
        }
    )
    when(toolBarState){
        ToolBarState.CLOSE->{
            DefaultToolBarListScreen(
                searchClick = {
                    sharedViewModel.toolBarState.value=ToolBarState.OPEN
                },
                filterClick = { sharedViewModel.saveState(it) },
                delteClick = {
                    openAlert=true
                }
            )
        }
        else->{
            SearchToolBar(
                text = toolBarSearchValue,
                onCloseClik = {
                    sharedViewModel.toolBarState.value=ToolBarState.CLOSE
                    sharedViewModel.toolbarSearchValue.value=""
                },
                onSearchClick = {
                                sharedViewModel.getSearshTask(it)
                },
                onTextChanged = {
                    sharedViewModel.toolbarSearchValue.value=it
                }
            )
        }
    }
}
@Composable
fun HandleDeleteClick(
    sharedViewModel: SharedViewModel
) {


}

@Composable
fun DefaultToolBarListScreen(
    searchClick: () -> Unit,
    filterClick: (Priority) -> Unit,
    delteClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "tasks", color = sec_text) },
        actions = {
            ToolBarAction(
                searchClick = searchClick,
                filterClick = filterClick,
                delteClick = delteClick
            )
        },
        backgroundColor = sec_back
    )
}

@Composable
fun ToolBarAction(
    searchClick: () -> Unit,
    filterClick: (Priority) -> Unit,
    delteClick: () -> Unit,
) {
    SearchAction(searchClick = searchClick)
    FilterAction(filterClick = filterClick)
    MenuAction(delteClick = delteClick)

}

@Composable
fun SearchAction(
    searchClick: () -> Unit
) {
    IconButton(onClick = {
        searchClick()
    }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "",
            tint = sec_text
        )
    }
}

@Composable
fun FilterAction(
    filterClick: (Priority) -> Unit
) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "",
            tint = sec_text
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                filterClick(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                filterClick(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                filterClick(Priority.NONE)
            }) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun MenuAction(
    delteClick: () -> Unit
) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "",
            tint = sec_text
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                delteClick()
            }) {
                Text(text = "Delte All", modifier = Modifier.padding(all = small_padding))
            }
        }
    }
}


@Composable
fun SearchToolBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClik: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    var isEmpty by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(toolbar_height),
        color = sec_back
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChanged(it)
            },
            leadingIcon = {
                IconButton(onClick = {
                    onSearchClick(text)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            },
            placeholder = { Text(text = "Search") },
            trailingIcon = {
                IconButton(onClick = {
                    if (isEmpty)
                    {
                        onTextChanged("")
                        isEmpty=false
                    }else{
                        if (text.isNotEmpty())
                        {
                            onTextChanged("")
                        }else{
                            onCloseClik()
                            isEmpty=true
                        }
                    }

                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = ""
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClick(text)
            }),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = main_text,
                disabledIndicatorColor =main_text,
                focusedIndicatorColor = main_text,
                unfocusedIndicatorColor = main_text,
                backgroundColor = sec_back
            )
        )
    }

}







