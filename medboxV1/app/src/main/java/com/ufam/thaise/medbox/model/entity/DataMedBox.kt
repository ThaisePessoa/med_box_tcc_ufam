package com.ufam.thaise.medbox.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class DataMedBox(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String? = null,
    var amount: String? = null
)
