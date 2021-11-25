package com.example.core.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val gender: String,
    val address: String,
    val age: Int,
    val phoneNumber: String
)
