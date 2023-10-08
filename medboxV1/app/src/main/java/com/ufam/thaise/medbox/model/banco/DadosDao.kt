package com.ufam.thaise.medbox.model.banco

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ufam.thaise.medbox.model.entity.DataMedBox

@Dao
interface DadosDao {
    @Query("SELECT * FROM DataMedBox WHERE id = :id")
    fun getMedBoxId(id: Long): DataMedBox?

    @Query("SELECT * FROM DataMedBox")
    fun getAll(): List<DataMedBox>

    @Query("DELETE FROM DataMedBox")
    fun deleteAll()

    @Insert
    fun saveMedBox(vararg dados: DataMedBox)

    @Delete
    fun deleteMedBox(vararg dados: DataMedBox)

    @Update
    fun editMedBox(dados: DataMedBox)

}