package com.arch.example.data.repository

import androidx.lifecycle.MutableLiveData
import com.arch.example.R
import com.arch.example.base.Data
import com.arch.example.base.SingleLiveEvent
import com.arch.example.data.local.dao.DogInfo
import com.arch.example.data.models.DogsListModel
import com.arch.example.data.models.DogsRandomModel
import com.arch.example.data.storage.LocalStorage
import com.arch.example.data.storage.RemoteStorage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


class RepositoryImpl(
    private val remoteStorage: RemoteStorage,
    private val localStorage: LocalStorage
) : RepositoryDogs {

    override fun getListOfDogs(response: MutableLiveData<Data<DogsListModel>?>) {
        sendRequestFromRemote(
            response = response,
            observable = remoteStorage.getListOfDogs()
        ) {
            it.apply {
                images.add("android.resource://com.arch.example/" + R.drawable.balrog)
            }
        }
    }

    override fun getRandomImage(response: MutableLiveData<Data<DogsRandomModel>?>) {
        sendRequestFromRemote(response, remoteStorage.getRandomImage()) { it }
    }

    override fun getListOfDogsFromDB(response: MutableLiveData<Data<DogsListModel>?>) {
        makeSingleQuery(response, localStorage.getAllDogsInfo()) {
            val model = DogsListModel(mutableListOf(), "")
            for (dogInfo in it) {
                model.images.add(dogInfo.imageUrl)
            }
            model
        }
    }

    override fun nukeDogInfoTable(callback: SingleLiveEvent<Boolean>) {
        makeCompletableQuery(callback, localStorage.nukeDogInfoTable())
    }

    override fun saveListOfDogs(callback: SingleLiveEvent<Boolean>, dogs: List<DogInfo>) {
        makeCompletableQuery(callback, localStorage.insertListOfDogInfo(dogs))
    }


    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    private fun <T, R> sendRequestFromRemote(
        response: MutableLiveData<Data<R>?>,
        observable: Observable<T>,
        mapper: (T) -> (R)
    ) {
        response.postValue(Data.loading())

        observable
            .compose(applyObservableAsync())
            .map {
                mapper(it)
            }
            .subscribe({ model ->
                response.postValue(Data.success(model))
            }, { error ->
                response.postValue(Data.error(error))
            })
    }

    private fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
            it.observeOn(AndroidSchedulers.mainThread())
        }
    }


    private fun <T, R> makeSingleQuery(
        data: MutableLiveData<Data<R>?>,
        single: Single<T>,
        mapper: (T) -> (R)
    ) {
        data.postValue(Data.loading())

        single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                mapper(it)
            }.subscribe({ model ->
                data.postValue(Data.success(model))
            }, { throwable ->
                data.postValue(Data.error(throwable))
            })
    }

    private fun makeCompletableQuery(
        event: SingleLiveEvent<Boolean>? = null,
        completable: Completable
    ) {

        completable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                event?.postValue(true)
            }, {
                event?.postValue(false)
            })
    }
}