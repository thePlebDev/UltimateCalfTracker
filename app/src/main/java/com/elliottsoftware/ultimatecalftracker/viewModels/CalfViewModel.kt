package com.elliottsoftware.ultimatecalftracker.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.elliottsoftware.ultimatecalftracker.models.Calf
import com.elliottsoftware.ultimatecalftracker.repositories.CalfRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CalfViewModel(private val repository: CalfRepository): ViewModel() {

    val allCalves: LiveData<List<Calf>> = repository.allCalves.asLiveData()


    fun insert(calf: Calf) = viewModelScope.launch {
        repository.insert(calf)
    }
    fun delete(calf:Calf) = viewModelScope.launch {
        repository.delete(calf)
    }


     suspend fun findCalf(calfId:Long):Calf{
        val deferred:Deferred<Calf> = viewModelScope.async {
            repository.findCalf(calfId)
        }
        return deferred.await()
    }

    fun updateCalf(calf:Calf) = viewModelScope.launch {
        repository.updateCalf(calf)
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