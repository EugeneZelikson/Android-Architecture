package com.arch.example.data.storage

import com.arch.example.data.api.DogAPI
import com.arch.example.data.models.DogsListModel
import com.arch.example.data.models.DogsRandomModel
import io.reactivex.rxjava3.core.Observable

class RemoteStorage(private val dogAPI: DogAPI) {

    fun getListOfDogs() : Observable<DogsListModel> {
        return dogAPI.getPicturesOfDogs()
    }

    fun getRandomImage() : Observable<DogsRandomModel> {
        return dogAPI.getRandomImage()
    }
}