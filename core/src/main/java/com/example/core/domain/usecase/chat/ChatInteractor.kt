package com.example.core.domain.usecase.chat

import com.example.core.data.Resource
import com.example.core.domain.model.Chat
import com.example.core.domain.repository.IChatRepository
import kotlinx.coroutines.flow.Flow

class ChatInteractor(private val chatRepo: IChatRepository):ChatUseCase {
    override suspend fun getChats(userId: String): Flow<Resource<List<Chat>>> =
        chatRepo.getChats(userId)

    override fun sendChat(chat: Chat) {
        chatRepo.sendChat(chat)
    }
}