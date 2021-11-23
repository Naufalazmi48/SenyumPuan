package com.example.core.domain.usecase.chat

import com.example.core.data.Resource
import com.example.core.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatUseCase {
    suspend fun getChats(userId: String): Flow<Resource<List<Chat>>>
    fun sendChat(chat: Chat)
}