package com.ufam.thaise.medbox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.repository.MedBoxRepository

class AddMedicineViewModel : ViewModel() {
    val repository = MedBoxRepository
    val toastMensage = MutableLiveData<String?>()
    val numberAmount = MutableLiveData<String?>()
    init {
        toastMensage.value = null
        numberAmount.value =null
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

    fun handleSave(dataSave: DataMedBox) {
        dataSave.apply {
            if (!name.isNullOrEmpty() && amount != "0"){
                repository.save(dataSave)
            }else{
                if (name.isNullOrEmpty()){
                    handleToastMensage("Preencha o nome")
                }else{
                    handleToastMensage("A quantidade não pode ser 0")
                }
            }
        }
    }

}