package com.example.viewmodelstrain.wayarchcomponents

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.example.viewmodelstrain.TAG

class MyPersistentModel2 : DefaultLifecycleObserver /*or LifecycleEventObserver*/ {

    companion object {
        val PROVIDER = "MY_PROVIDER_2"
        val KEY = "key_x"
    }

    var x: Int = 0

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val savedOwner = owner as SavedStateRegistryOwner?
        savedOwner?.savedStateRegistry?.let {
            it.registerSavedStateProvider(PROVIDER) {
                save()
            }
            // Get the previously saved state and restore it
            val state: Bundle? = it.consumeRestoredStateForKey(PROVIDER)
            restore(state)
        }
    }

    private fun save(): Bundle {
        Log.e(TAG, "MyPersistentModel2.saveState: $x")
        return bundleOf(KEY to x)
    }

    private fun restore(bundle: Bundle?) {
        x = bundle?.getInt(KEY) ?: 0
    }
}