package com.ufam.thaise.medbox.viewmodel

sealed class DataBaseResult {
    object Success : DataBaseResult()
    data class Error(val message: String) : DataBaseResult()
}