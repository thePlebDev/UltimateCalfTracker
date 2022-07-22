package com.elliottsoftware.ultimatecalftracker.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.elliottsoftware.ultimatecalftracker.R
import com.elliottsoftware.ultimatecalftracker.database.CalfApplication
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentAddCalfBinding
import com.elliottsoftware.ultimatecalftracker.models.Calf
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModel
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModelFactory
import com.google.android.material.textfield.TextInputEditText
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
            val tagNumber:String = binding.editTextTitle.text.toString()
            val details:String = binding.editTextDescription.text.toString()
            val cciaNumber:String = binding.editTextCciaNumber.text.toString()
            val sex:String = buttonIsChecked(binding.radioBull)
            saveCalf(tagNumber,details,cciaNumber,sex,it)

        }
    }


    private fun buttonIsChecked(radioButton: RadioButton):String{
        return if(radioButton.isChecked){
            "Bull"
        }else{
            "Heifer"
        }
    }
    private fun saveCalf(tagNumber: String,details:String,cciaNumber:String,sex:String,view:View){
        if(!validateTagNumber(tagNumber)){
            // this should run if the tagNumber is not empty
            calfViewModel.insert(Calf(tagNumber,cciaNumber,sex,details,Date()))
            Navigation.findNavController(view).navigate(R.id.action_addCalfFragment_to_mainFragment)
        }
    }

    private fun validateTagNumber(tagNumber:String):Boolean{
        //if statements are expressions in Kotlin
        return if(tagNumber.isEmpty()){
            binding.editTextTitle.error = "Field can not be empty"
            true;
        }else{
            false;
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}