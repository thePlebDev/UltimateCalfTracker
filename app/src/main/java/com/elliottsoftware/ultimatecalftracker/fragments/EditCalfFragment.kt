package com.elliottsoftware.ultimatecalftracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.elliottsoftware.ultimatecalftracker.R
import com.elliottsoftware.ultimatecalftracker.database.CalfApplication
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentAddCalfBinding
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentEditCalfBinding
import com.elliottsoftware.ultimatecalftracker.models.Calf
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModel
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [EditCalfFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditCalfFragment : Fragment() {
    //todo: still have to hook up all the buttons and everything for fragment_add_calf
    private var _binding:FragmentAddCalfBinding? = null
    private val binding get() =_binding!!

    private val args: EditCalfFragmentArgs by navArgs()

    private lateinit var cancelButton:FloatingActionButton
    private lateinit var updateButton:FloatingActionButton
    private lateinit var  updateTagNumber: TextInputEditText
    private lateinit var updateDetails:TextInputEditText
    private lateinit var updateCCIANumber:TextInputEditText
    private lateinit var updateSexBULL: RadioButton
    private lateinit var updateSexHEIFER: RadioButton
    private lateinit var calfDate:Date
    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }
    private lateinit var foundCalf:Calf



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCalfBinding.inflate(inflater,container,false)
        cancelButton = binding.newCalfFabLeft;
        updateButton = binding.newCalfFabRight;
        updateTagNumber = binding.editTextTitle
        updateDetails = binding.editTextDescription
        updateCCIANumber = binding.editTextCciaNumber
        updateSexBULL = binding.radioBull
        updateSexHEIFER = binding.radioHeifer

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_editCalfFragment_to_mainFragment)
        }
        updateButton.setOnClickListener{
            updateCalf(it,updateTagNumber.text.toString(),
                updateDetails.text.toString(),updateCCIANumber.text.toString(),updateSexBULL.isChecked)
        }

        CoroutineScope(IO).launch {

            setCalfOnMainThread(args.calfId)

        }



    }

    private suspend fun setCalfOnMainThread(calfId:Long){
        CoroutineScope(Main).launch{
           // Log.e("setCalfOnMainThread thread ---->",Thread.currentThread().name)
           foundCalf = calfViewModel.findCalf(calfId)

            updateTagNumber.setText(foundCalf.tagNumber)
            updateDetails.setText(foundCalf.details)
            updateCCIANumber.setText(foundCalf.CCIANumber)
            calfDate = foundCalf.date
            if (foundCalf.sex == "Bull"){
                updateSexBULL.isChecked = true
            }else{
                updateSexHEIFER.isChecked = true
            }
        }
    }

    private fun updateCalf(it:View,tagNumber: String,details:String,cciaNumber: String,isBull:Boolean){
         if(!tagNumberIsEmpty(tagNumber)){
             val sex = checkSex(isBull)
             calfViewModel.updateCalf(Calf(tagNumber,cciaNumber,sex,details,calfDate,args.calfId))
             Navigation.findNavController(it).navigate(R.id.action_editCalfFragment_to_mainFragment)
         }


    }

    private fun tagNumberIsEmpty(tagNumber:String):Boolean{
        //if statements are expressions in Kotlin
        return if(tagNumber.isEmpty()){
            updateTagNumber.error = "Field can not be empty"
            true;
        }else{
            false;
        }

    }

    private fun checkSex(isBull: Boolean):String{
        return if(isBull){
            "Bull"
        }else{
            "Heifer"
        }
    }






















    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}