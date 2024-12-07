package com.example.viewmodelstrain.wayhilt.models

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.viewmodelstrain.TAG
import com.example.viewmodelstrain.model.JustModel
import javax.inject.Inject

//@HiltViewModel
class HwSavedViewModel1 @Inject constructor(
    private val justModel: JustModel,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var x: Int = savedStateHandle.get<Int>("x") ?: 0
        set(value) {
            field = value
            savedStateHandle.set("x", value)
        }

    fun printMe() {
        Log.e(TAG, "--- HwSavedViewModel1: ${justModel.message}")
    }
}

//@HiltViewModel
class HwSavedViewModel2 @Inject constructor(
    // justName: String, // Hilt is not able to inject primitives, they must be passed in Bundle
    private val justModel: JustModel,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val NAME_KEY = "NAME_KEY"
    }

    // declare the var here instead of constructor
    private lateinit var justName: String

    var x: Int = savedStateHandle.get<Int>("x") ?: 0
        set(value) {
            field = value
            savedStateHandle.set("x", value)
        }

    fun printMe() {
        Log.e("hey", "--- HwSavedViewModel2: ${justModel.message}, my name is: $justName")
    }

    // Use this fabric method, as Hilt doesn't support default arguments OOTB
    fun create(defaultArgs: Bundle) {
        justName = savedStateHandle.get<String>(NAME_KEY) ?: defaultArgs.getString(NAME_KEY) ?: "John Doe"
    }
}
