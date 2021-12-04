package com.example.core.domain.model

data class Desa(
    val name:String,
    val progress: Int,
    val latitude: Double,
    val longitude: Double,
    val pictures: List<String> = ArrayList()
)
