package com.elliottsoftware.ultimatecalftracker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

}