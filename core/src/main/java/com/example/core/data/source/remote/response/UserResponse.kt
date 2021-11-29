package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("gender")
    val gender: String? = null,
    @field:SerializedName("address")
    val address: String? = null,
    @field:SerializedName("age")
    val age: Int? = null,
    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @field:SerializedName("role")
    val role: String? = null
)