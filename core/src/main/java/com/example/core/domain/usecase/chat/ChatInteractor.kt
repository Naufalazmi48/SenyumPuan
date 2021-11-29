package com.example.core.domain.usecase.chat

import com.example.core.data.Resource
import com.example.core.domain.model.Chat
import com.example.core.domain.repository.IChatRepository
import com.example.core.domain.repository.IUserRepository
import com.example.core.presentation.model.UserWithChat
import kotlinx.coroutines.flow.*

class ChatInteractor(private val chatRepo: IChatRepository, private val userRepo: IUserRepository):ChatUseCase {
    override suspend fun getChats(userId: String): Flow<Resource<List<Chat>>> =
        chatRepo.getChats(userId)

    override fun sendChat(chat: Chat) {
        chatRepo.sendChat(chat)
    }

    override suspend fun getChatAllUser(): Flow<Resource<List<UserWithChat>>> = flow {
        chatRepo.getChatAllUser().collect {
            when (it) {
                is Resource.Success -> {
                    val listUser = arrayListOf<UserWithChat>()
                    it.data?.forEach { chat ->
                        print(chat)
                        userRepo.getUser(chat.receiverId).take(2).last().data?.let { user ->
                            val userWithChat = UserWithChat(user, chat)
                            listUser.add(userWithChat)
                        }
                    }

                    listUser.sortByDescending { userWithChat ->
                        userWithChat.chat.dateTimeSend
                    }
                    emit(Resource.Success(listUser))
                }
                is Resource.Error -> emit(Resource.Error<List<UserWithChat>>(it.message.toString()))
                is Resource.Loading -> emit(Resource.Loading())
            }
        }
    }
}