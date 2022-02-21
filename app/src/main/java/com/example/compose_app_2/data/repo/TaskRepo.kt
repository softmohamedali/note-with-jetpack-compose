package com.example.compose_app_2.data.repo

import com.example.compose_app_2.data.Dao
import com.example.compose_app_2.data.models.Task
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
@ViewModelScoped
class TaskRepo @Inject constructor(
    var taskDao: Dao
){
    val getAllNote=taskDao.getAllNote()
    val getNoteHighPrio=taskDao.getNoteHighPriority()
    val getNoteLowPrio=taskDao.getNoteLowPriority()

    suspend fun insertNote(task: Task)=
        taskDao.insertNote(task)

    suspend fun upDateNote(task: Task)=
        taskDao.UpdateNote(task)

    suspend fun deleAllNote()=
        taskDao.deleteAll()

    suspend fun deleteNote(task: Task)=
        taskDao.deleteNote(task)

    fun searshNote(query:String)=
        taskDao.searchNote(query)

    fun getNote(taskId:Int)=taskDao.getSpeceficNote(noteid = taskId)


}