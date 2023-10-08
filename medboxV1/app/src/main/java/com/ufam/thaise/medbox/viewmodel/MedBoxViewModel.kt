package com.ufam.thaise.medbox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.repository.MedBoxRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedBoxViewModel @Inject constructor(private val repository: MedBoxRepositoryInterface) :
    ViewModel() {
    val toastMensage = MutableLiveData<String?>()
    val numberAmount = MutableLiveData<String?>()
    val saveSuccess = MutableLiveData<String?>()
    val disableAdd = MutableLiveData<Boolean>()

    init {
        toastMensage.value = null
        numberAmount.value = null
        saveSuccess.value = null
        disableAdd.value = false
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
                // Operação de salvamento bem-sucedida, faça o que for necessário
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
       if(getAllMedBox().size >= 3){
           disableAdd.value = true
           toastMensageMedBox("Todos os compartimentos estão cheios")
       }
    }

}