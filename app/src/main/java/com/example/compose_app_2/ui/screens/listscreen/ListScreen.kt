package com.example.compose_app_2.ui.screens

import android.view.Display
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_app_2.R
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.data.models.Task
import com.example.compose_app_2.ui.componente.TaskItem
import com.example.compose_app_2.ui.screens.listscreen.DefaultToolBarListScreen
import com.example.compose_app_2.ui.screens.listscreen.ToolBarListScreen
import com.example.compose_app_2.ui.theme.*
import com.example.compose_app_2.utils.Action
import com.example.compose_app_2.utils.StatusResult
import com.example.compose_app_2.utils.ToolBarState
import com.example.compose_app_2.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navToTaskScreeen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val tasks by sharedViewModel.allTask.collectAsState()
    val searchTasks by sharedViewModel.searshTask.collectAsState()
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTask()
        sharedViewModel.getState()
    }
    val scaffoldState = rememberScaffoldState()
    val toolBarSearchValue by sharedViewModel.toolbarSearchValue
    val toolBarState by sharedViewModel.toolBarState
    val state by sharedViewModel.stste.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val action by sharedViewModel.action

    ShowSnakBar(
        scaffoldState = scaffoldState,
        title = sharedViewModel.title.value,
        action = action,
        handleDBAction = { sharedViewModel.handleDBAction(action) },
        undoDelteTask = { sharedViewModel.action.value = Action.UNDO }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            ListScreenContent(
                navToTaskScreeen,
                tasks,
                searchTask = searchTasks,
                toolBarState = toolBarState,
                state = state,
                highPriortyTasks = highPriorityTasks,
                lowPriortyTasks = lowPriorityTasks
            )
        },
        floatingActionButton = { FabListScreen(navToTaskScreeen) },
        topBar = {
            ToolBarListScreen(
                sharedViewModel = sharedViewModel,
                toolBarSearchValue = toolBarSearchValue,
                toolBarState = toolBarState
            )
        },
    )
}

@Composable
fun FabListScreen(
    navToTaskScreeen: (Int) -> Unit
) {
    FloatingActionButton(
        onClick = { navToTaskScreeen(-1) },
        backgroundColor = my_orange
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_task),
            tint = sec_text
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    navToTaskScreeen: (Int) -> Unit,
    tasks: List<Task>
) {
    LazyColumn {
        items(items = tasks, key = { it.id }) {
            TaskItem(task = it, navigateToTaskScreen = navToTaskScreeen)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ListScreenContent(
    navToTaskScreeen: (Int) -> Unit,
    tasks: StatusResult<List<Task>>,
    toolBarState: ToolBarState,
    searchTask: StatusResult<List<Task>>,
    state: StatusResult<Priority>?,
    highPriortyTasks: List<Task>,
    lowPriortyTasks: List<Task>,
) {
    if (state is StatusResult.OnSuccess) {
        when {
            toolBarState == ToolBarState.TRIGER -> {
                if (searchTask is StatusResult.OnSuccess) {
                    HandleListTask(listTask = searchTask.data, navToTaskScreeen = navToTaskScreeen)
                }
            }
            state.data == Priority.NONE ->{
                if (tasks is StatusResult.OnSuccess) {
                    HandleListTask(listTask = tasks.data, navToTaskScreeen = navToTaskScreeen)
                }
            }
            state.data ==Priority.LOW ->{
                HandleListTask(listTask = lowPriortyTasks, navToTaskScreeen = navToTaskScreeen)
            }
            state.data ==Priority.HIGH ->{
                HandleListTask(listTask = highPriortyTasks, navToTaskScreeen = navToTaskScreeen)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListTask(
    listTask: List<Task>,
    navToTaskScreeen: (Int) -> Unit
) {
    if (listTask.isEmpty()) {
        DisplayEmptyContent()
    } else {
        DisplayTasks(
            navToTaskScreeen = navToTaskScreeen,
            tasks = listTask
        )
    }
}

@Composable
fun DisplayEmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_sad),
            contentDescription = "sad face",
            tint = gray
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "No Tasks Found", color = gray, fontSize = 20.sp)
    }
}

@Composable
fun ShowSnakBar(
    scaffoldState: ScaffoldState,
    action: Action,
    title: String,
    undoDelteTask: (Action) -> Unit,
    handleDBAction: () -> Unit
) {
    handleDBAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action)
    {
        if (action != Action.NONE) {
            scope.launch {
                val result = scaffoldState.snackbarHostState.showSnackbar(
                    message = massageText(
                        action = action,
                        title = title
                    ),
                    actionLabel = labelText(action = action)
                )
                undoTask(
                    action = action,
                    snackbarResult = result,
                    undoTask = undoDelteTask
                )
            }
        }
    }

}

fun massageText(action: Action, title: String): String {
    return if (action == Action.DELETE_ALL) "All Tasks Deleted" else "${action.name} : ${title}"
}

fun labelText(action: Action): String {
    return if (action == Action.DELETE) "UNDO" else "OK"
}

fun undoTask(
    action: Action,
    undoTask: (Action) -> Unit,
    snackbarResult: SnackbarResult

) {
    if (action == Action.DELETE && snackbarResult == SnackbarResult.ActionPerformed) {
        undoTask(Action.UNDO)
    }
}



