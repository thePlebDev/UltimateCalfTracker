package com.elliottsoftware.ultimatecalftracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.elliottsoftware.ultimatecalftracker.R
import com.elliottsoftware.ultimatecalftracker.database.CalfApplication
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentAddCalfBinding
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentMainBinding
import com.elliottsoftware.ultimatecalftracker.models.Calf
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModel
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModelFactory
import java.util.*


/**
 * A simple [Fragment] subclass.

 */
class AddCalfFragment : Fragment() {
    private var _binding:FragmentAddCalfBinding? = null;
    private val binding:FragmentAddCalfBinding get() = _binding!!

    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCalfBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newCalfFabLeft.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_addCalfFragment_to_mainFragment)
        }
        binding.newCalfFabRight.setOnClickListener{
            val calf:Calf = Calf("ee3r","vvr4","BULL","another one", Date())
            calfViewModel.insert(calf)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}