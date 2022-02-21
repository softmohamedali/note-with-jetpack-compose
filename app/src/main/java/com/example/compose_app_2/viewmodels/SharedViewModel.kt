package com.example.compose_app_2.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_app_2.data.models.Priority
import com.example.compose_app_2.data.models.Task
import com.example.compose_app_2.data.repo.DataStoreRepo
import com.example.compose_app_2.data.repo.TaskRepo
import com.example.compose_app_2.utils.Action
import com.example.compose_app_2.utils.StatusResult
import com.example.compose_app_2.utils.ToolBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private var repo:TaskRepo,
    private var dataStoreRepo: DataStoreRepo
) :ViewModel(){
    val toolBarState:MutableState<ToolBarState> = mutableStateOf(ToolBarState.CLOSE)
    private val _taskById:MutableStateFlow<Task?> = MutableStateFlow(null)
    private var _allTask= MutableStateFlow<StatusResult<List<Task>>>(StatusResult.OnInit)
    private var _searshTask= MutableStateFlow<StatusResult<List<Task>>>(StatusResult.OnInit)

    val id= mutableStateOf(0)
    val title= mutableStateOf("")
    val description= mutableStateOf("")
    val priority= mutableStateOf(Priority.LOW)
    val action= mutableStateOf(Action.NONE)

    val toolbarSearchValue:MutableState<String> = mutableStateOf("")
    val allTask:StateFlow<StatusResult<List<Task>>> =_allTask
    val searshTask:StateFlow<StatusResult<List<Task>>> =_searshTask
    val taskById:StateFlow<Task?> =_taskById
    val lowPriorityTasks:StateFlow<List<Task>> =repo.getNoteLowPrio.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val highPriorityTasks:StateFlow<List<Task>> =repo.getNoteHighPrio.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun getAllTask(){
        _allTask.value=StatusResult.OnLoading
        try {
            viewModelScope.launch {
                repo.getAllNote.collect {
                    _allTask.value=StatusResult.OnSuccess(it)
                }
            }
        }catch (e:Throwable){
            _allTask.value=StatusResult.OnError(e)
        }
    }
    private fun deletAllTasks(){
        viewModelScope.launch {
            repo.deleAllNote()
        }
    }
    fun getSearshTask(query:String){
        _searshTask.value=StatusResult.OnLoading
        try {
            viewModelScope.launch {
                repo.searshNote("%$query%").collect {
                    _searshTask.value=StatusResult.OnSuccess(it)
                }
            }
        }catch (e:Throwable){
            _searshTask.value=StatusResult.OnError(e)
        }
        toolBarState.value=ToolBarState.TRIGER
    }

    fun saveState(priority: Priority)
    {
        viewModelScope.launch {
            dataStoreRepo.saveStste(priority = priority)
        }
    }
    val _state= MutableStateFlow<StatusResult<Priority>?>(null)
    val stste:StateFlow<StatusResult<Priority>?> =_state
    fun getState(){
        _state.value=StatusResult.OnLoading
        try {
            viewModelScope.launch {
                dataStoreRepo.readState.map { Priority.valueOf(it) }.collect {
                    _state.value=StatusResult.OnSuccess(it)
                }
            }
        }catch (e:Throwable){
            _state.value=StatusResult.OnError(e)
        }
    }

    fun getTaskById(taskId:Int)
    {
        viewModelScope.launch {
            repo.getNote(taskId).collect{
                _taskById.value=it
            }
        }
    }

    private fun addTask()
    {
        viewModelScope.launch (Dispatchers.IO){
            val task=Task(
                title = title.value,
                priority = priority.value,
                note = description.value
            )
            repo.insertNote(task = task)
        }
        toolBarState.value=ToolBarState.CLOSE
    }



    fun handleDBAction(maction: Action)
    {

        when(maction){
            Action.ADD->{
                addTask()
            }
            Action.DELETE->{
                delteTaskDB()
            }
            Action.UNDO->{
                addTask()

            }
            Action.UPDATE->{
                upDateTaskDB()

            }
            Action.NONE->{

            }
            Action.DELETE_ALL->{
                deletAllTasks()
            }
        }
        action.value=Action.NONE
    }


    fun upDateTaskDB(){
        viewModelScope.launch (Dispatchers.IO){
            val task=Task(
                id = id.value,
                title = title.value,
                priority = priority.value,
                note = description.value
            )
            repo.upDateNote(task = task)
        }
        toolBarState.value=ToolBarState.CLOSE
    }

    fun delteTaskDB(){
        viewModelScope.launch (Dispatchers.IO){
            val task=Task(
                id = id.value,
                title = title.value,
                priority = priority.value,
                note = description.value
            )
            repo.deleteNote(task = task)
        }
    }


    fun updatetask(task:Task?)
    {
        if (task!=null)
        {
            id.value=task.id
            title.value=task.title
            description.value=task.note
            priority.value=task.priority
        }else{
            id.value=0
            title.value=""
            description.value=""
            priority.value=Priority.LOW
        }
    }

    fun validateFields():Boolean
    {
        if (title.value.isEmpty()&&description.value.isEmpty())
        {
            return false
        }
        return true
    }

}