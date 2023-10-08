package com.ufam.thaise.medbox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.repository.MedBoxRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class AddMedicineViewModel @Inject constructor (private val repository: MedBoxRepositoryInterface): ViewModel() {
    val toastMensage = MutableLiveData<String?>()
    val numberAmount = MutableLiveData<String?>()
    val saveSuccess = MutableLiveData<String?>()

    init {
        toastMensage.value = null
        numberAmount.value =null
        saveSuccess.value = null
    }
    fun handleTxtAmountAdd(txtNumber: Int) {
        if (txtNumber >= 64) {
            handleToastMensage("Você chegou ao limite de comprimidos que é 64")
        } else {
            numberAmount.value = (txtNumber + 1).toString()
        }
    }
    fun handleTxtAmountminus(txtNumber: Int) {
        if(txtNumber <= 0){
            handleToastMensage("Sua quantidade está em 0")
        }else {
            numberAmount.value = (txtNumber - 1).toString()
        }
    }
    fun handleToastMensage(msg: String?){
        toastMensage.value = msg
    }
    suspend fun handleSave(dataSave: DataMedBox) {
            val result: DataBaseResult
            dataSave.apply {
                if (!name.isNullOrEmpty() && amount != "0") {
                    result = repository.save(dataSave)
                } else {
                    if (name.isNullOrEmpty()) {
                        handleToastMensage("Preencha o nome")
                    } else {
                        handleToastMensage("A quantidade não pode ser 0")
                    }
                    result = DataBaseResult.Error("Erro ao salvas dados")
                }
                when (result) {
                    is DataBaseResult.Success -> {
                        // Operação de salvamento bem-sucedida, faça o que for necessário
                        // Exibir uma mensagem de sucesso, navegar para outra tela, etc.
                        saveSuccess.value = "Salvo com sucesso"
                    }

                    is DataBaseResult.Error -> {
                        // Operação de salvamento falhou, trate o erro
                        handleToastMensage("Erro: ${result.message}")
                    }
                }
            }

    }
//    fun handleSave(dataSave: DataMedBox) {
//        dataSave.apply {
//            if (!name.isNullOrEmpty() && amount != "0"){
//                viewModelScope.launch {
//                    repository.save(dataSave)
//
//                }
//            }else{
//                if (name.isNullOrEmpty()){
//                    handleToastMensage("Preencha o nome")
//                }else{
//                    handleToastMensage("A quantidade não pode ser 0")
//                }
//            }
//        }
//    }

}