package com.elliottsoftware.ultimatecalftracker.repositories

import androidx.annotation.WorkerThread
import com.elliottsoftware.ultimatecalftracker.dao.CalfDao
import com.elliottsoftware.ultimatecalftracker.models.Calf
import kotlinx.coroutines.flow.Flow


//declares the DAO as a private property in the constructor. Pass in the DAO
//instead of the whole database, because we only needs to access the DAO
class CalfRepository(private val calfDao: CalfDao) {

    //Room executes all queries on a separate thread
    // Observed Flow will notify the observer when the data has changed
    val allCalves: Flow<List<Calf>> = calfDao.getAllCalves()

    //by default Room runs suspend queries off the main thread, therefore, we don't need to
    //implement anything else to ensure we're not doing long running database work off the main thread
    @WorkerThread
    suspend fun insert(calf: Calf){
        calfDao.insert(calf)
    }

    @WorkerThread
    suspend fun delete(calf: Calf){
        calfDao.deleteCalf(calf)
    }

    @WorkerThread
    suspend fun findCalf(calfId:Long):Calf{
        return calfDao.findCalf(calfId)
    }

    @WorkerThread
    suspend fun updateCalf(calf:Calf){
        calfDao.updateCalf(calf)
    }




























}