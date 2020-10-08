package com.arch.example.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arch.example.base.Data
import com.arch.example.data.models.DogsRandomModel
import com.arch.example.data.repository.RepositoryDogs

class ImageViewModel @ViewModelInject constructor(
    private val repositoryDogs: RepositoryDogs
) : ViewModel() {

    val responseLiveData = MutableLiveData<Data<DogsRandomModel>?>()

    fun getRandomImage() {
        repositoryDogs.getRandomImage(responseLiveData)
    }

}