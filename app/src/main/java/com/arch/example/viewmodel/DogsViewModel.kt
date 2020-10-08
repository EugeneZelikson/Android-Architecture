package com.arch.example.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.arch.example.base.BaseViewModel
import com.arch.example.base.Data
import com.arch.example.data.local.dao.DogInfo
import com.arch.example.data.models.DogsListModel
import com.arch.example.data.repository.RepositoryDogs
import java.util.*

class DogsViewModel @ViewModelInject constructor(
    private val repository: RepositoryDogs
) : BaseViewModel() {

    val dogsResponseRemote = responseLiveData<DogsListModel>()
    val dogsResponseDB = responseLiveData<DogsListModel>()
    val clearDataBaseCallback = completableCallback()
    val saveToDataBaseCallback = completableCallback()

    val dogsList = MutableLiveData<DogsListModel>()

    val isDataFromDataBase = MutableLiveData<Boolean?>(null)

    fun getDogsListFromDB() {
        repository.getListOfDogsFromDB(dogsResponseDB)
    }

    fun getDogsListFromRemote() {
        repository.getListOfDogs(dogsResponseRemote)
    }

    fun clearDataBaseClick() {
        repository.nukeDogInfoTable(clearDataBaseCallback)
    }

    fun reloadClick() {
        getDogsListFromDB()
    }

    fun saveToDataBaseClick() {
        dogsList.value?.let {
            val dogs = mutableListOf<DogInfo>()
            for (url in it.images) {
                dogs.add(DogInfo(imageUrl = url))
            }

            repository.saveListOfDogs(saveToDataBaseCallback, Collections.unmodifiableList(dogs))
        }
    }

    fun remoteDogsListResponse(response: Data<DogsListModel>) {
        when (response.status) {
            Data.Status.LOADING -> {
                isProgressBarVisible.postValue(true)
            }
            Data.Status.SUCCESS -> {
                response.data?.let { model ->
                    isDataFromDataBase.postValue(false)
                    showMessageEvent.postValue("Loaded from API")
                    dogsList.postValue(model)
                }

                responseCompleted(dogsResponseRemote)
            }
            Data.Status.ERROR -> {
                displayResponseError(response.message, dogsResponseRemote)
            }
        }
    }

    fun localDogsListResponse(response: Data<DogsListModel>) {
        when (response.status) {
            Data.Status.LOADING -> {
                isProgressBarVisible.postValue(true)
            }
            Data.Status.SUCCESS -> {
                if (response.data?.images.isNullOrEmpty()) {
                    getDogsListFromRemote()
                } else {
                    isDataFromDataBase.postValue(true)
                    showMessageEvent.postValue("Loaded from Database")
                    dogsList.postValue(response.data)
                }

                responseCompleted(dogsResponseDB)
            }
            Data.Status.ERROR -> {
                displayResponseError(response.message, dogsResponseDB)
            }
        }
    }

}