package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    suspend fun getChats(userId: String): Flow<Resource<List<Chat>>>
    suspend fun getChatAllUser(): Flow<Resource<List<Chat>>>
    fun sendChat(chat: Chat)
}