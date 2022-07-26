package com.elliottsoftware.ultimatecalftracker.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
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
    // The usage of an interface lets you inject your own implementation




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
//        setHasOptionsMenu(true) //we can still use this but it is depreciated
        //todo: change this out into a different class
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.options_menu, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView // this now behaves as a EditText
                //going to create a extension function
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**todo: NEED TO FAIL GRACEFULLY IF THERE IS NO POSITION FOUND
     * //currentyl just crashes if there is no calf found
     */
    override fun onDeleteCalfClick(calfId: Long) {
      val calf: List<Calf>? =  calfViewModel.allCalves.value
        val foundCalf:Calf? = calf?.find { calf: Calf -> calf.id == calfId  }
        calfViewModel.delete(foundCalf!!)

    }

    override fun onEditCalfClick(calfId:Long) {
            val action = MainFragmentDirections.actionMainFragmentToEditCalfFragment(calfId)
            Navigation.findNavController(binding.root).navigate(action)
    }




}