package com.example.viewmodelstrain

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.example.viewmodelstrain.databinding.ActivityMainBinding
import com.example.viewmodelstrain.wayandroid.MyAndroidFragment
import com.example.viewmodelstrain.wayarchcomponents.MyArchComponentsFragment
import com.example.viewmodelstrain.waydagger.MyDaggerFragment
import com.example.viewmodelstrain.wayhilt.MyHiltFragment
import dagger.hilt.android.AndroidEntryPoint

// https://developer.android.com/topic/libraries/architecture/saving-states
// https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate
// https://habr.com/ru/company/wrike/blog/569918/
// https://cvoronin.medium.com/viewmodel-savedstatehandle-and-dagger-2-31-assisted-injection-52191f7e9da2
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, "onCreate")
        Log.e(TAG, "---=== WAS KILLED: ${checkProcessIsRestored(savedInstanceState)}")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnArchComponentsWay.setOnClickListener {
            //showFragment(MyArchComponentsFragment.newInstance())
            Handler(Looper.getMainLooper()).post { printButton("Handler 1") }
            binding.btnArchComponentsWay.post { printButton("post") }
            Handler(Looper.getMainLooper()).post { printButton("Handler 2") }
        }
        binding.btnAndroidWay.setOnClickListener {
            showFragment(MyAndroidFragment.newInstance())
        }
        binding.btnDaggerWay.setOnClickListener {
            showFragment(MyDaggerFragment.newInstance())
        }
        binding.btnHiltWay.setOnClickListener {
            showFragment(MyHiltFragment.newInstance())
        }

        if (savedInstanceState == null) {
            setupFragments()
        }

        // listen to result
        supportFragmentManager.setFragmentResultListener(
            FRAGMENT_RESULT_KEY, this
        ) { requestKey, bundle ->
            Log.e(TAG, "FragmentResult: $requestKey, ${bundle.getString(BUNDLE_X_KEY)}")
        }

        printButton("onCreate")
        Handler(Looper.getMainLooper()).post { printButton("Handler 1") }
        binding.btnArchComponentsWay.post { printButton("post") }
        Handler(Looper.getMainLooper()).post { printButton("Handler 2") }
        binding.btnArchComponentsWay.doOnAttach { printButton("doOnAttach") }
        binding.btnArchComponentsWay.doOnLayout { printButton("doOnLayout") }
        binding.btnArchComponentsWay.doOnPreDraw { printButton("doOnPreDraw") }
        //binding.btnArchComponentsWay.viewTreeObserver.addOnPreDrawListener { printButton("addOnGlobalLayoutListener") }
    }

    override fun onResume() {
        super.onResume()
        printButton("onResume")
    }

    private fun printButton(from: String) {
        val width = binding.btnArchComponentsWay.width
        Log.e(TAG, "*** BTN WIDTH from $from is $width, is attached ${ binding.btnArchComponentsWay.isAttachedToWindow}")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "--- a: onstop")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, fragment, "tag")
            .addToBackStack(null)
            .commit()
    }

    private fun setupFragments() {
       with (supportFragmentManager) {
            if (backStackEntryCount == 0) {
                Log.e(TAG, "setupFragments")
                showFragment(MyArchComponentsFragment.newInstance())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("processId", application.toString())
        Log.e(TAG, "--- a: onSaveInstanceState")
    }

    private fun checkProcessIsRestored(savedInstanceState: Bundle?): Boolean {
        if (savedInstanceState == null ) {
            Log.e(TAG, "1")
            return false
        } else if (savedInstanceState.getString("processId") == null) {
            Log.e(TAG, "2")
            return false
        } else if (savedInstanceState.getString("processId") == application.toString()) {
            Log.e(TAG, "3")
            return false
        } else {
            Log.e(TAG, "4")
            return true
        }
    }
}