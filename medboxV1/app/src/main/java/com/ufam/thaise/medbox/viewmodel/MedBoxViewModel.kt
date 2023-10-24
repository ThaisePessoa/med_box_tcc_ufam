package com.ufam.thaise.medbox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.ufam.thaise.medbox.Constant
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.repository.MedBoxRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedBoxViewModel @Inject constructor(private val repository: MedBoxRepositoryInterface) :
    ViewModel() {
    val dataTemp = MutableLiveData<DataMedBox?>()
    val toastMensage = MutableLiveData<String?>()
    val numberAmount = MutableLiveData<String?>()
    val saveSuccess = MutableLiveData<String?>()
    val disableAdd = MutableLiveData<Boolean>()
    val deleteSuccess = MutableLiveData<String?>()
    val toCheckList = MutableLiveData<Boolean>()
    val editSuccess = MutableLiveData<String?>()

    init {
        dataTemp.value = Constant.data
        toastMensage.value = null
        numberAmount.value = null
        saveSuccess.value = null
        disableAdd.value = false
        deleteSuccess.value = null
        toCheckList.value = false
        editSuccess.value = null
    }

    fun TxtAmountAddMedBox(txtNumber: Int) {
        if (txtNumber >= 64) {
            toastMensageMedBox("Você chegou ao limite de comprimidos que é 64")
        } else {
            numberAmount.value = (txtNumber + 1).toString()
        }
    }

    fun handleTxtAmountminus(txtNumber: Int) {
        if (txtNumber <= 0) {
            toastMensageMedBox("Sua quantidade está em 0")
        } else {
            numberAmount.value = (txtNumber - 1).toString()
        }
    }

    fun toastMensageMedBox(msg: String?) {
        toastMensage.value = msg
    }

    suspend fun saveMedBox(dataSave: DataMedBox) {
        val result = if (dataSave.name.isNullOrEmpty()) {
            DataBaseResult.Error("Preencha o nome")
        } else if (dataSave.amount == "0") {
            DataBaseResult.Error("A quantidade não pode ser 0")
        } else {
            repository.save(dataSave)
        }

        when (result) {
            is DataBaseResult.Success -> {
                // Operação de salvamento bem-sucedida
                // Exibir uma mensagem de sucesso, navegar para outra tela, etc.
                saveSuccess.value = "Salvo com sucesso"
            }

            is DataBaseResult.Error -> {
                // Operação de salvamento falhou, trate o erro
                toastMensageMedBox("${result.message}")
            }
        }
    }


    suspend fun getAllMedBox(): List<DataMedBox> {
        return repository.getAll()
    }

    suspend fun toCheckAdd() {
        if (getAllMedBox().size >= 3) {
            disableAdd.value = true
            toastMensageMedBox("Todos os compartimentos estão cheios")
        } else
            disableAdd.value = false
    }
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("fir-medbox-default-rtdb")

    suspend fun openToMedBox(comp: String) {
        reference.child("compartimento").setValue(comp)
        toastMensageMedBox("compartimento ${comp} esta sendo aberto")
    }

    suspend fun deleteMedBox() {
        dataTemp.value?.let {
            val result =
                repository.delete(it)
            when (result) {
                is DataBaseResult.Success -> {
                    // Operação de delete bem-sucedida
                    // Exibir uma mensagem de sucesso, navegar para outra tela, etc.
                    deleteSuccess.value = "Deletado com sucesso"
                    toCheckAdd()
                }
                is DataBaseResult.Error -> {
                    // Operação de delete falhou, trate o erro
                    toastMensageMedBox("${result.message}")
                }
            }
        }
    }

    suspend fun toCheckListVoid() {
        if (getAllMedBox().isEmpty()) {
            toCheckList.value = true
            toastMensageMedBox("Você precisa cadastrar medicamento")
        } else
            toCheckList.value = false
    }

    suspend fun editMedBox(data: DataMedBox) {
        data.id = dataTemp.value?.id!!
        val result = if (data.name.isNullOrEmpty()) {
            DataBaseResult.Error("Preencha o nome")
        } else if (data.amount == "0") {
            DataBaseResult.Error("A quantidade não pode ser 0")
        } else {
            repository.edit(data)
        }

        when (result) {
            is DataBaseResult.Success -> {
                // Operação de editar bem-sucedida
                // Exibir uma mensagem de sucesso, navegar para outra tela, etc.
                editSuccess.value = "Alterado com sucesso"
                dataTemp.value = data
                Constant.data = data
            }

            is DataBaseResult.Error -> {
                // Operação de salvamento falhou, trate o erro
                toastMensageMedBox("${result.message}")
            }
        }
    }
}