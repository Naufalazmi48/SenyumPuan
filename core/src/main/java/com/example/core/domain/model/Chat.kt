package com.example.core.domain.model

data class Chat(
    val senderId: String,
    val receiverId: String,
    val dateTimeSend: String,
    val message: String,
)
