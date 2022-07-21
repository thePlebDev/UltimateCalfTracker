package com.elliottsoftware.ultimatecalftracker.database

import android.app.Application
import com.elliottsoftware.ultimatecalftracker.repositories.CalfRepository

class CalfApplication :Application() {
    // Using by lazy so the database and the repository are only created when they're
    //needed, rather than when the applications starts

    private val database by lazy { CalfDatabase.getDatabase(this) }
    val repository by lazy { CalfRepository(database.calfDao()) }

}