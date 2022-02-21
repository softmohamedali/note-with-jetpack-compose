package com.example.compose_app_2.data.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.compose_app_2.data.models.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
val Context.dataSore:DataStore<Preferences> by preferencesDataStore(name = "mypre")
@ViewModelScoped
class DataStoreRepo @Inject constructor(
    @ApplicationContext private var context:Context
) {
    private val dataStore=context.dataSore
    private object PrefrencesKeys{
        val sortState= stringPreferencesKey("state")
    }
    suspend fun saveStste(priority: Priority){
        dataStore.edit {
            it[PrefrencesKeys.sortState]=priority.name
        }
    }

    val readState:Flow<String> =dataStore.data
        .catch {
            if (it is IOException)
            {
                emit(emptyPreferences())
            }else{
                throw it
            }
        }.map {
            val state=it[PrefrencesKeys.sortState]?:Priority.NONE.name
            state
        }

}