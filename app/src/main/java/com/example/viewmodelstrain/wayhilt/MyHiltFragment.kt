package com.example.viewmodelstrain.wayhilt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelstrain.TAG
import com.example.viewmodelstrain.databinding.FragmentMyHiltBinding
import com.example.viewmodelstrain.model.JustModel
import com.example.viewmodelstrain.wayhilt.models.HwModelWithInnerInjection
import com.example.viewmodelstrain.wayhilt.models.HwSavedViewModel1
import com.example.viewmodelstrain.wayhilt.models.HwSavedViewModel2
import com.example.viewmodelstrain.wayhilt.models.HwSimpleViewModel1
import com.example.viewmodelstrain.wayhilt.models.HwSimpleViewModel2
import com.example.viewmodelstrain.wayhilt.models.HwSimpleViewModel3
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyHiltFragment : Fragment() {

    private var binding: FragmentMyHiltBinding? = null

    @Inject
    lateinit var justModel: JustModel

    // ViewModel without a saved state
    lateinit var simpleViewModel1: HwSimpleViewModel1
    lateinit var simpleViewModel2: HwSimpleViewModel2
    lateinit var simpleViewModel3: HwSimpleViewModel3
    @Inject
    lateinit var simpleViewModel3Factory: HwSimpleViewModel3.Factory

    // ViewModel with a saved state
    lateinit var savedViewModel1: HwSavedViewModel1
    lateinit var savedViewModel2: HwSavedViewModel2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyHiltBinding.inflate(inflater, container, false)
        setupViews()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "--- onstop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "--- f: onSaveInstanceState")
    }

    private fun initViewModels() {

        // MODELS INJECTION

        // Injected model from HiltModule
        justModel.printMe()

        // inner injection
        val modelWithInnerInjection = HwModelWithInnerInjection()
        modelWithInnerInjection.printMe(requireActivity().applicationContext)

        // --- VIEW MODELS WITHOUT SAVED STATE

        // empty constructor
        simpleViewModel1 = ViewModelProvider(this).get(HwSimpleViewModel1::class.java)

        // JustModel is injected in constructor
        simpleViewModel2 = ViewModelProvider(this).get(HwSimpleViewModel2::class.java)
        simpleViewModel2.printMe()

        simpleViewModel3 = ViewModelProvider(this, simpleViewModel3Factory).get(HwSimpleViewModel3::class.java)
        simpleViewModel3.printMe()

        // --- VIEW MODELS WITH SAVED STATE

        // without default args
        savedViewModel1 = ViewModelProvider(this).get(HwSavedViewModel1::class.java)
        savedViewModel1.printMe()

        // with default args
        savedViewModel2 = ViewModelProvider(this).get(HwSavedViewModel2::class.java)
        savedViewModel2.create(bundleOf(HwSavedViewModel2.NAME_KEY to "Slim Shady"))
        savedViewModel2.printMe()
    }

    private fun setupViews() = binding?.apply {
        myTxt1.text = simpleViewModel1.x.toString()
        myTxt2.text = simpleViewModel2.x.toString()
        myTxt3.text = simpleViewModel3.x.toString()
        myTxt4.text = savedViewModel1.x.toString()
        myTxt5.text = savedViewModel2.x.toString()

        myBtn1.setOnClickListener {
            simpleViewModel1.x++
            myTxt1.text = simpleViewModel1.x.toString()
        }
        myBtn2.setOnClickListener {
            simpleViewModel2.x++
            myTxt2.text = simpleViewModel2.x.toString()
        }
        myBtn3.setOnClickListener {
            simpleViewModel3.x++
            myTxt3.text = simpleViewModel3.x.toString()
        }
        myBtn4.setOnClickListener {
            savedViewModel1.x++
            myTxt4.text = savedViewModel1.x.toString()
        }
        myBtn5.setOnClickListener {
            savedViewModel2.x++
            myTxt5.text = savedViewModel2.x.toString()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyHiltFragment().apply {
                arguments = Bundle().apply {
                    Log.e(TAG, "create MyHiltFragment")
                }
            }
    }
}