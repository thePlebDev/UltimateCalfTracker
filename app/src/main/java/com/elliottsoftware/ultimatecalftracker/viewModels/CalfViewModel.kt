package com.elliottsoftware.ultimatecalftracker.viewModels

import androidx.lifecycle.*
import com.elliottsoftware.ultimatecalftracker.models.Calf
import com.elliottsoftware.ultimatecalftracker.repositories.CalfRepository
import kotlinx.coroutines.launch

class CalfViewModel(private val repository: CalfRepository): ViewModel() {

    val allCalves: LiveData<List<Calf>> = repository.allCalves.asLiveData()


    fun insert(calf: Calf) = viewModelScope.launch {
        repository.insert(calf)
    }
}

class CalfViewModelFactory(private val repository: CalfRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalfViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalfViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}