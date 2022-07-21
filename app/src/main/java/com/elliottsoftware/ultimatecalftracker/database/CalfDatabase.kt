package com.elliottsoftware.ultimatecalftracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elliottsoftware.ultimatecalftracker.dao.CalfDao
import com.elliottsoftware.ultimatecalftracker.models.Calf
import com.elliottsoftware.ultimatecalftracker.typeConverters.DateConverter

@Database(entities = [Calf::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
public abstract class CalfDatabase : RoomDatabase() {

    abstract fun calfDao():CalfDao

    companion object{
        //Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: CalfDatabase? = null;

        fun getDatabase(context:Context): CalfDatabase{
            //if the INSTANCE is not null, then return it, if it is, then create the database

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalfDatabase::class.java,
                    "calf_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}