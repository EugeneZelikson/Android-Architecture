package com.arch.example.data.storage

import com.arch.example.data.local.dao.DogInfo
import com.arch.example.data.local.dao.DogsInfoDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocalStorage(private val dogsInfoDao: DogsInfoDao) {

    fun getAllDogsInfo(): Single<List<DogInfo>> {
        return dogsInfoDao.getAllDogsInfo()
    }

    fun nukeDogInfoTable(): Completable {
        return dogsInfoDao.nukeTable()
    }

    fun insertListOfDogInfo(dogs: List<DogInfo>): Completable {
        return dogsInfoDao.insertAll(dogs)
    }
}