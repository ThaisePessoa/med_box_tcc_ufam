package com.ufam.thaise.medbox.repository

import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.viewmodel.DataBaseResult

interface MedBoxRepositoryInterface {
    suspend fun getAll(): List<DataMedBox>
    suspend fun save(data: DataMedBox): DataBaseResult
    suspend fun delete(data: DataMedBox): DataBaseResult
    suspend fun edit(data: DataMedBox): DataBaseResult
}