package com.ufam.thaise.medbox.repository

import androidx.lifecycle.LiveData
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.viewmodel.DataBaseResult

interface MedBoxRepositoryInterface {
    fun getAll(): LiveData<List<DataMedBox>>
    suspend fun save(dataSave: DataMedBox): DataBaseResult
}