package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DesaResponse(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("progress")
    val progress: Int? = null,
    @field:SerializedName("latitude")
    val latitude: Double? = null,
    @field:SerializedName("longitude")
    val longitude: Double? = null,
)