package com.example.viewmodelstrain.wayandroid

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.viewmodelstrain.TAG

class AwSavedViewModel1(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var x: Int = savedStateHandle.get<Int>("x") ?: 0
        set(value) {
            field = value
            savedStateHandle.set("x", value)
        }
}

open class AwSavedViewModel2(
    private val message: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val MSG2_KEY = "MSG2_KEY"
    }

    var x: Int = savedStateHandle.get<Int>("x") ?: 0
        set(value) {
            field = value
            savedStateHandle.set("x", value)
        }

    fun printMe() {
        val message2 = savedStateHandle.get<String>(MSG2_KEY)
        Log.e(TAG, "Hi, I'm AwSavedViewModel2: $message $message2 $this")
    }

    class Factory(
        private val message: String,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return AwSavedViewModel2(message, handle) as T
        }
    }
}
