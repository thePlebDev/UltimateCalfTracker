package com.elliottsoftware.ultimatecalftracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elliottsoftware.ultimatecalftracker.R
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentAddCalfBinding
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentMainBinding


/**
 * A simple [Fragment] subclass.

 */
class AddCalfFragment : Fragment() {
    private var _binding:FragmentAddCalfBinding? = null;
    private val binding:FragmentAddCalfBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCalfBinding.inflate(inflater,container,false)

        return binding.root
        
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}