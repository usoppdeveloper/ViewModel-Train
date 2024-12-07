package com.example.viewmodelstrain.waydagger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.viewmodelstrain.TAG
import com.example.viewmodelstrain.databinding.FragmentMyDaggerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyDaggerFragment : Fragment() {

    private var binding: FragmentMyDaggerBinding? = null

    @Inject
    lateinit var assistedFactory: DwSavedViewModel.DwSimpleViewModelAssistedFactory
    private val simpleViewModel: DwSavedViewModel by viewModels {
        val defaultArgs = bundleOf(DwSavedViewModel.NAME_KEY to "Hello from DaggerFragment")
        assistedFactory.create("hey ya", this, defaultArgs)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyDaggerBinding.inflate(inflater, container, false)
        setupViews()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupViews() = binding?.apply {
        simpleViewModel.printMe()

        myTxt1.text = simpleViewModel.x.toString()

        myBtn1.setOnClickListener {
            simpleViewModel.x++
            myTxt1.text = simpleViewModel.x.toString()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyDaggerFragment().apply {
                arguments = Bundle().apply {
                    Log.e(TAG, "create MyDaggerFragment")
                }
            }
    }

}