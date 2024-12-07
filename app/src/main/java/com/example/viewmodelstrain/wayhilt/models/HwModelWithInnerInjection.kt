package com.example.viewmodelstrain.wayhilt.models

import android.content.Context
import android.util.Log
import com.example.viewmodelstrain.TAG
import com.example.viewmodelstrain.model.JustModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class HwModelWithInnerInjection {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface MyEntryPointInterface {
        fun justModel(): JustModel
    }

    fun printMe(context: Context) {
        val myEntryPointInterface = EntryPointAccessors.fromApplication<MyEntryPointInterface>(context)
        Log.e(TAG, "ModelWithInnerInjection ${myEntryPointInterface.justModel().message}")
    }
}