package com.arch.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arch.example.data.local.dao.DogInfo
import com.arch.example.data.local.dao.DogsInfoDao

@Database(entities = [DogInfo::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getDogsInfoDao(): DogsInfoDao
}