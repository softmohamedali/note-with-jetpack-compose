package com.example.compose_app_2.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compose_app_2.utils.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class Task (
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var title:String,
    var note:String,
    var priority: Priority
        ){
}