package com.example.viewmodelstrain.wayandroid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.get
import com.example.viewmodelstrain.TAG
import com.example.viewmodelstrain.databinding.FragmentMyAndroidBinding
import com.example.viewmodelstrain.wayandroid.viewmodels.AwLiveDataViewModel

class MyAndroidFragment : Fragment() {

    companion object {
        fun newInstance() = MyAndroidFragment()
    }

    var binding: FragmentMyAndroidBinding? = null

    private val liveDataViewModel: AwLiveDataViewModel by viewModels()

    lateinit var simpleViewModel1: AwSimpleViewModel1 // by viewModels()
    lateinit var simpleViewModel2: AwSimpleViewModel2 // by viewModels { AwSimpleViewModel2.Factory("hey") }

    private lateinit var savedViewModel1: AwSavedViewModel1
    private lateinit var savedViewModel2: AwSavedViewModel2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAndroidBinding.inflate(inflater, container, false)
        setupViews()
        return binding!!.root
    }

    private fun setupViews() {
        binding?.apply {
            liveDataViewModel.x.observe(viewLifecycleOwner) { x ->
                Log.e(TAG, "LiveData x: $x")
                myTxt5.text = x.toString()
            }

            myTxt1.text = simpleViewModel1.x.toString()
            myTxt2.text = simpleViewModel2.x.toString()
            myTxt3.text = savedViewModel1.x.toString()
            myTxt4.text = savedViewModel2.x.toString()

            myBtn1.setOnClickListener {
                simpleViewModel1.x++
                myTxt1.text = simpleViewModel1.x.toString()
            }
            myBtn2.setOnClickListener {
                simpleViewModel2.x++
                myTxt2.text = simpleViewModel2.x.toString()
            }
            myBtn3.setOnClickListener {
                savedViewModel1.x++
                myTxt3.text = savedViewModel1.x.toString()
            }
            myBtn4.setOnClickListener {
                savedViewModel2.x++
                myTxt4.text = savedViewModel2.x.toString()
            }
            myBtn5.setOnClickListener {
                liveDataViewModel.increaseX()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModels()
    }

    private fun setupViewModels() {
        // --- WITHOUT SAVED STATE

        // default factory
        simpleViewModel1 = ViewModelProvider(this).get()

        // custom factory
        val simpleFactory = AwSimpleViewModel2.Factory("hey")
        simpleViewModel2 = ViewModelProvider(this, simpleFactory).get()
        simpleViewModel2.printMe()

        // --- WITH SAVED STATE

        // default factory
        savedViewModel1 = ViewModelProvider(this).get()

        // custom factory without default values
        val defaultArgs = bundleOf(AwSavedViewModel2.MSG2_KEY to "Hello there") // can be null
        val savedFactory = AwSavedViewModel2.Factory("hey ya", this, defaultArgs)
        savedViewModel2 = ViewModelProvider(this, savedFactory).get()
        savedViewModel2.printMe()
    }
}