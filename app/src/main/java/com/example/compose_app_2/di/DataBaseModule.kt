package com.example.compose_app_2.di

import android.content.Context
import androidx.room.Room
import com.example.compose_app_2.data.MyDataBase
import com.example.compose_app_2.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context)=Room.databaseBuilder(
        context,
        MyDataBase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(dataBase: MyDataBase)=dataBase.getDao()
}