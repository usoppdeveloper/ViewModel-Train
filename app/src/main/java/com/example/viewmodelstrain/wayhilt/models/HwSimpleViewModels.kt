package com.example.viewmodelstrain.wayhilt.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelstrain.TAG
import com.example.viewmodelstrain.model.JustModel
import javax.inject.Inject

/**
 * После кучи танцев с бубном, обновленный проект так и не собирает
 * с включенными аннотациями @HiltViewModel для HwSimpleViewModel1 & HwSimpleViewModel2
 * Нужно заново разбираться видимо с хилтом, чтобы это заработало
 */

/**
 * Just a view model that survives after config changes.
 */
//@HiltViewModel
class HwSimpleViewModel1 @Inject constructor() : ViewModel() {
    var x = 0
}

//@HiltViewModel
class HwSimpleViewModel2 @Inject constructor(
    val justModel: JustModel
) : ViewModel() {
    var x = 0

    fun printMe() {
        Log.e(TAG, "HwSimpleViewModel2: ${justModel.message}")
    }
}

/**
 * It can't be marked as @HiltViewModel as primitives can't be injected.
 * A fabric must be used here or all primitives must be passed in bundle
 */
//@HiltViewModel
class HwSimpleViewModel3 /*@Inject constructor*/(
    val message: String,
    val justModel: JustModel
) : ViewModel() {
    var x = 0

    fun printMe() {
        Log.e(TAG, "HwSimpleViewModel3: $message + ${justModel.printMe()}")
    }

    class Factory @Inject constructor(
        val message: String,
        val justModel: JustModel
    ): ViewModelProvider.Factory {

        init {
            Log.e(TAG, "--- hey, going to create a new fabric $this")
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HwSimpleViewModel3("$message $this", justModel) as T
        }
    }
}