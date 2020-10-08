package com.arch.example.data.api

import com.arch.example.data.models.DogsListModel
import com.arch.example.data.models.DogsRandomModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface DogAPI {

    companion object {
        const val DOG_BASE_URL = "https://dog.ceo/api/"
    }

    @GET("breeds/image/random/15")
    fun getPicturesOfDogs(): Observable<DogsListModel>

    @GET("breeds/image/random")
    fun getRandomImage(): Observable<DogsRandomModel>
}