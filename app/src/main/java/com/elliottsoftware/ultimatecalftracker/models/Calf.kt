package com.elliottsoftware.ultimatecalftracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Calf(

    val tagNumber: String,
    val CCIANumber: String,
    val sex:String,
    val details:String,
    val date: Date,
    @PrimaryKey(autoGenerate = true)
    val id: Long =0
) {

}