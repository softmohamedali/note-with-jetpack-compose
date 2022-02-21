package com.example.compose_app_2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_app_2.data.models.Task

@Database(
    version =1,
    entities = arrayOf(Task::class),
    exportSchema = false
)
abstract class MyDataBase:RoomDatabase (){
    abstract fun getDao():Dao
}