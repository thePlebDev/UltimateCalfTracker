package com.elliottsoftware.ultimatecalftracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.elliottsoftware.ultimatecalftracker.R
import com.elliottsoftware.ultimatecalftracker.database.CalfApplication
import com.elliottsoftware.ultimatecalftracker.databinding.FragmentMainBinding
import com.elliottsoftware.ultimatecalftracker.models.Calf
import com.elliottsoftware.ultimatecalftracker.recyclerViews.CalfListAdapter
import com.elliottsoftware.ultimatecalftracker.recyclerViews.OnCalfListener
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModel
import com.elliottsoftware.ultimatecalftracker.viewModels.CalfViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), OnCalfListener {
    private var _binding: FragmentMainBinding? = null
    private val binding:FragmentMainBinding get() = _binding!!
    private val calfViewModel:CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navNewCalf.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_addCalfFragment)
        }
        val recyclerView = binding.recyclerview
        val adapter = CalfListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        //add an observer on the LiveData returned by allCalves();
        //the onChanged method fires when the observed data changes and the activity is in the foreground
        calfViewModel.allCalves.observe(viewLifecycleOwner, Observer { calves ->
            calves?.let{adapter.submitList(it)}
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**todo: NEED TO FAIL GRACEFULLY IF THERE IS NO POSITION FOUND
     */
    override fun onDeleteCalfClick(position: Long) {
      val calf: List<Calf>? =  calfViewModel.allCalves.value
        val foundCalf:Calf? = calf?.find { calf: Calf -> calf.id == position  }
        calfViewModel.delete(foundCalf!!)

    }


}