package com.example.viewmodelstrain.wayarchcomponents

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import com.example.viewmodelstrain.TAG

class MyPersistentModel1(
    registryOwner: SavedStateRegistryOwner
) : SavedStateRegistry.SavedStateProvider {

    companion object {
        val PROVIDER = "MY_PROVIDER_1"
        val KEY = "key_x"
    }

    var x : Int = 0

    init {
        Log.e(TAG, "init MyPersistentModel")

        // Register a LifecycleObserver for when the Lifecycle hits ON_CREATE
        registryOwner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    registryOwner.savedStateRegistry.let {
                        // Register this object for future calls to saveState()
                        it.registerSavedStateProvider(PROVIDER, this)
                        // Get the previously saved state and restore it
                        val state: Bundle? = it.consumeRestoredStateForKey(PROVIDER)

                        restore(state)
                    }
                }
                else -> {}
            }
        })
    }

    override fun saveState(): Bundle {
        Log.e(TAG, "MyPersistentModel.saveState: $x")
        return bundleOf(KEY to x)
    }

    private fun restore(bundle: Bundle?) {
        x = bundle?.getInt(KEY) ?: 0
    }
}