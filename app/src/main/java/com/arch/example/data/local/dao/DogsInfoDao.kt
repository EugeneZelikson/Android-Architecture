package com.arch.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface DogsInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(dogs: List<DogInfo>): Completable

    @Query("SELECT * FROM dogInfo")
    fun getAllDogsInfo(): Single<List<DogInfo>>

    @Query("DELETE FROM dogInfo")
    fun nukeTable(): Completable
}