package com.example.compose_app_2.data

import androidx.room.*
import androidx.room.Dao
import com.example.compose_app_2.data.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note:Task)

    @Update
    suspend fun UpdateNote(note:Task)

    @Delete
    suspend fun deleteNote(note:Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllNote(): Flow<List<Task>>

    @Query("SELECT * FROM tasks where id=:noteid")
    fun getSpeceficNote(noteid:Int): Flow<Task>

    @Query("SELECT * FROM tasks WHERE title like :searchQ")
    fun searchNote(searchQ:String): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY CASE When priority like 'N%' then 1 When priority like 'M%' then 2 When priority like 'H%' then 3 end")
    fun getNoteLowPriority(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY CASE When priority like 'H%' then 1 When priority like 'M%' then 2 When priority like 'L%' then 3 end")
    fun getNoteHighPriority(): Flow<List<Task>>


}