package com.ufam.thaise.medbox.repository

import androidx.lifecycle.LiveData
import com.ufam.thaise.medbox.model.entity.DataMedBox

interface MedBoxRepositoryInterface {
    fun getAll(): LiveData<List<DataMedBox>>
    fun save(dataSave: DataMedBox)
}