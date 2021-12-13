package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DesaResponse(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("deescription")
    val description: String? = null,
    @field:SerializedName("latitude")
    val latitude: Double? = null,
    @field:SerializedName("longitude")
    val longitude: Double? = null,
    @field:SerializedName("pictures")
    val pictures: List<String>? = null,
)