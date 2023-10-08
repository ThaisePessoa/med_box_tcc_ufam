package com.ufam.thaise.medbox.repository

import com.ufam.thaise.medbox.model.banco.DadosDao
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.viewmodel.DataBaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedBoxRepository @Inject constructor(private val medBoxDao: DadosDao) :
    MedBoxRepositoryInterface {
    override suspend fun getAll(): List<DataMedBox> {
        // Execute a busca em uma coroutine com o contexto apropriado (Dispatchers.IO)

        return withContext(Dispatchers.IO){
            medBoxDao.getAll()
        }
    }

    override suspend fun save(data: DataMedBox): DataBaseResult {
        // Execute a inserção em uma coroutine com o contexto apropriado (Dispatchers.IO)
        // Execute a operação de inserção no banco de dados aqui
        return try {
            withContext(Dispatchers.IO) {
                medBoxDao.saveMedBox(data)
                DataBaseResult.Success
            }
        } catch (e: Exception) {
            DataBaseResult.Error(e.message ?: "Error")
        }

    }

    override suspend fun delete(data: DataMedBox): DataBaseResult {
        return try {
            withContext(Dispatchers.IO) {
                medBoxDao.deleteMedBox(data)
                DataBaseResult.Success
            }
        } catch (e: Exception) {
            DataBaseResult.Error(e.message ?: "Error")
        }
    }


}