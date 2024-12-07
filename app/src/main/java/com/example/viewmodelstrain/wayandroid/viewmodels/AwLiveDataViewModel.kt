package com.example.viewmodelstrain.wayandroid.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class AwLiveDataViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    val x = savedStateHandle.getLiveData<Int>("livedata", 0)

    fun increaseX() {
        x.postValue(x.value?.inc())
    }
}