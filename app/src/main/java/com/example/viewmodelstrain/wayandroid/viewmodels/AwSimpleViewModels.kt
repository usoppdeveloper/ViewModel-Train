package com.example.viewmodelstrain.wayandroid

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelstrain.TAG

/**
 * Just a view model that survives after config changes.
 */
class AwSimpleViewModel1() : ViewModel() {
    var x = 0
}

class AwSimpleViewModel2(val message: String) : ViewModel() {
    var x = 0

    fun printMe() {
        Log.e(TAG, "Hi, I'm AwSimpleViewModel2: $message")
    }

    class Factory constructor(
        val message: String
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AwSimpleViewModel2(message) as T
        }
    }
}