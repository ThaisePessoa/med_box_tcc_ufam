package com.ufam.thaise.medbox.repository

import androidx.lifecycle.LiveData
import com.ufam.thaise.medbox.model.banco.DadosDao
import com.ufam.thaise.medbox.model.entity.DataMedBox

class MedBoxRepository(private val dadosDao: DadosDao):MedBoxRepositoryInterface {

    override fun getAll(): LiveData<List<DataMedBox>> {
        return dadosDao.getFromMedBox()
    }

    override fun save(dataSave: DataMedBox) {
            dadosDao.saveMedBox(dataSave)
    }


}