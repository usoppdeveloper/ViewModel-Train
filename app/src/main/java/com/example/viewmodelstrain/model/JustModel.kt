package com.example.viewmodelstrain.model

import android.util.Log
import javax.inject.Inject

class JustModel @Inject constructor(
    val message: String
) {
    fun printMe() {
        Log.e("hey", "Hi, I'm JustModel: $message $this")
    }
}
