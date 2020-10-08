package com.arch.example.data.local.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogInfo")
data class DogInfo(

    @PrimaryKey
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String
)