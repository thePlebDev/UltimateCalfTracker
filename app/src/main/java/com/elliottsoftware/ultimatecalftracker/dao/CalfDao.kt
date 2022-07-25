package com.elliottsoftware.ultimatecalftracker.dao

import androidx.room.*
import com.elliottsoftware.ultimatecalftracker.models.Calf
import kotlinx.coroutines.flow.Flow

@Dao
interface CalfDao {

    @Query("SELECT * FROM calf ORDER BY id ASC")
    fun getAllCalves(): Flow<List<Calf>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(calf: Calf)

    @Query("DELETE FROM calf")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCalf( calf: Calf)

    @Update
    suspend fun updateCalf(calf:Calf)

    @Query("SELECT * FROM calf WHERE calf.id==:calfId")
     suspend fun findCalf(calfId:Long):Calf










}