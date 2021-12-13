package com.example.core.data.source.repository

import com.example.core.data.Resource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.Chat
import com.example.core.domain.repository.IChatRepository
import com.example.core.utils.Mapper.mapChatResponseToDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class ChatRepository(private val remoteDataSource: RemoteDataSource): IChatRepository{
    override suspend fun getChats(userId: String): Flow<Resource<List<Chat>>> = flow {
        emit(Resource.Loading())
            remoteDataSource.getChats(userId).collect {
                when (it) {
                    is ApiResponse.Empty -> emit(Resource.Success(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                    is ApiResponse.Success -> emit(Resource.Success(mapChatResponseToDomain(it.data)))
                }
            }
    }

    override fun sendChat(chat: Chat) {
        remoteDataSource.sendChat(chat)
    }

    override suspend fun getChatAllUser(): Flow<Resource<List<Chat>>> = flow {
        emit(Resource.Loading())
        remoteDataSource.getChatAllUser().collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Success(emptyList()))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(mapChatResponseToDomain(it.data)))
            }
        }
    }
}