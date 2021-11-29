package com.example.core.domain.model

data class Chat(
    val senderId: String,
    val senderRole: String,
    val receiverId: String,
    val dateTimeSend: Long,
    val message: String,
)
