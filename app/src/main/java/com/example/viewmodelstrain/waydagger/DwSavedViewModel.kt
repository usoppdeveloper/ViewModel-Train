package com.example.viewmodelstrain.waydagger

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.viewmodelstrain.TAG
import com.example.viewmodelstrain.model.JustModel
import com.example.viewmodelstrain.wayhilt.models.HwSavedViewModel2
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

// No any annotations here, it will be created by fabric
class DwSavedViewModel(
    private val message1: String?,
    private val message2: String?,
    private val justModel: JustModel,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val NAME_KEY = "NAME_KEY"
    }

    var x: Int = savedStateHandle.get<Int>("x") ?: 0
        set(value) {
            field = value
            savedStateHandle.set("x", value)
        }

    fun printMe() {
        Log.e(TAG, "DwSimpleViewModel: $x, $message1 $message2, ${justModel.message}")
    }

    // @AssistedInject works only with constructor,
    // The factory can't be created in a Dagger module
    class Factory @AssistedInject constructor(
        @Assisted val message1: String,
        val justModel: JustModel,
        @Assisted owner: SavedStateRegistryOwner,
        @Assisted defaultArgs: Bundle
    ): AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T = DwSavedViewModel(
            message1,
            handle.get<String>(HwSavedViewModel2.NAME_KEY),
            justModel,
            handle
        ) as T
    }

    @AssistedFactory
    interface DwSimpleViewModelAssistedFactory {
        fun create(message1: String, owner: SavedStateRegistryOwner, defaultArgs: Bundle): Factory
    }
}
