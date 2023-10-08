package com.ufam.thaise.medbox.repository

import androidx.lifecycle.LiveData
import com.ufam.thaise.medbox.model.banco.DadosDao
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.viewmodel.DataBaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedBoxRepository @Inject constructor(private val medBoxDao: DadosDao) :
    MedBoxRepositoryInterface {
    //    private val _saveResult = MutableLiveData<DataBaseResult>()
//    val saveResult: LiveData<DataBaseResult> = _saveResult
    override fun getAll(): LiveData<List<DataMedBox>> {

        return medBoxDao.getFromMedBox()
    }

    override suspend fun save(dataSave: DataMedBox): DataBaseResult {

        // Execute a inserção em uma coroutine com o contexto apropriado (Dispatchers.IO)

        // Execute a operação de inserção no banco de dados aqui
        return try {
            withContext(Dispatchers.IO) {
                medBoxDao.saveMedBox(dataSave)
                DataBaseResult.Success
            }
        } catch (e: Exception) {
            DataBaseResult.Error(e.message ?: "Error")
        }

    }


}