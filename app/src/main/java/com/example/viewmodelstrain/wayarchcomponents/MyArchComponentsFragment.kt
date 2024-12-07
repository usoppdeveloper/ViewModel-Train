package com.example.viewmodelstrain.wayarchcomponents

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.viewmodelstrain.BUNDLE_X_KEY
import com.example.viewmodelstrain.FRAGMENT_RESULT_KEY
import com.example.viewmodelstrain.databinding.FragmentMyArchComponentsBinding

class MyArchComponentsFragment : Fragment() {

    private var binding: FragmentMyArchComponentsBinding? = null

    // create every time,
    // the models save/restore it's state from savedState on every configuration change
    private var myPersistentModel1 = MyPersistentModel1(this)
    private var myPersistentModel2 = MyPersistentModel2()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(myPersistentModel2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyArchComponentsBinding.inflate(inflater, container, false)
        initViews()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViews() = binding?.apply {
        myTxt1.text = myPersistentModel1.x.toString()
        myTxt2.text = myPersistentModel2.x.toString()

        myBtn1.setOnClickListener {
            myPersistentModel1.x++
            myTxt1.text = myPersistentModel1.x.toString()

            // send a result
            parentFragmentManager.setFragmentResult(
                FRAGMENT_RESULT_KEY,
                bundleOf(BUNDLE_X_KEY to myPersistentModel1.x.toString())
            )
        }
        myBtn2.setOnClickListener {
            myPersistentModel2.x++
            myTxt2.text = myPersistentModel2.x.toString()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyArchComponentsFragment().apply {
                Log.e("hey", "create ArchComponentsFragment")
                arguments = Bundle().apply {
                }
            }
    }
}