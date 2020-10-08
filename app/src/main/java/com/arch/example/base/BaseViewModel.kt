package com.arch.example.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val showMessageEvent = SingleLiveEvent<String>()
    val isProgressBarVisible = MutableLiveData(false)

    protected fun <L> displayResponseError(message: String, liveData: MutableLiveData<L>) {
        liveData.postValue(null)
        isProgressBarVisible.postValue(false)
        showMessageEvent.postValue(message)
    }

    protected fun <L> responseCompleted(liveData: MutableLiveData<L>) {
        liveData.postValue(null)
        isProgressBarVisible.postValue(false)
    }

    protected fun BaseViewModel.completableCallback(): SingleLiveEvent<Boolean> = SingleLiveEvent()

    protected fun <D> BaseViewModel.responseLiveData(): MutableLiveData<Data<D>?> =
        MutableLiveData()

}