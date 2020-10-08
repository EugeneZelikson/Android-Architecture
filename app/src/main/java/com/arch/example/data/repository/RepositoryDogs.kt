package com.arch.example.data.repository

import androidx.lifecycle.MutableLiveData
import com.arch.example.base.SingleLiveEvent
import com.arch.example.base.Data
import com.arch.example.data.local.dao.DogInfo
import com.arch.example.data.models.DogsListModel
import com.arch.example.data.models.DogsRandomModel

interface RepositoryDogs {

    fun getListOfDogs(response: MutableLiveData<Data<DogsListModel>?>)

    fun getRandomImage(response: MutableLiveData<Data<DogsRandomModel>?>)

    fun getListOfDogsFromDB(response: MutableLiveData<Data<DogsListModel>?>)

    fun nukeDogInfoTable(callback: SingleLiveEvent<Boolean>)

    fun saveListOfDogs(callback: SingleLiveEvent<Boolean>, dogs: List<DogInfo>)

}