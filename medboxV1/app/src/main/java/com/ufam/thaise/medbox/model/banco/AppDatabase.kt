package com.ufam.thaise.medbox.model.banco

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufam.thaise.medbox.model.entity.DataMedBox

@Database(entities = [DataMedBox::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun medBoxDao(): DadosDao

}