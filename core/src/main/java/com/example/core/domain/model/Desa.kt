package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Desa(
    val name:String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val pictures: List<String>
): Parcelable
