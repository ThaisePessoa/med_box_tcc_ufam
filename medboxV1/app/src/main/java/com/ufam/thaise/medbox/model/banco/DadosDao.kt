package com.ufam.thaise.medbox.model.banco

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ufam.thaise.medbox.model.entity.DataMedBox

@Dao
interface DadosDao {
    //    Dados User
    @Query("SELECT * FROM DataMedBox WHERE id = :id")
    fun getMedBoxId(id: Long): DataMedBox?

    @Query("SELECT * FROM DataMedBox")
    fun getFromMedBox(): LiveData< List<DataMedBox>>

    @Insert
    fun saveMedBox(vararg dadosUser: DataMedBox)

    @Delete
    fun deleteMedBox(vararg dadosUser: DataMedBox)

    @Update
    fun editMedBox(dadosUser: DataMedBox)

}