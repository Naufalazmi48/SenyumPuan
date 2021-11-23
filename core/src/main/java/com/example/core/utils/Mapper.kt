package com.example.core.utils

import com.example.core.data.source.remote.response.ChatResponse
import com.example.core.data.source.remote.response.DesaResponse
import com.example.core.data.source.remote.response.UserResponse
import com.example.core.domain.model.Chat
import com.example.core.domain.model.Desa
import com.example.core.domain.model.User

object Mapper {

    fun mapChatResponseToDomain(list: List<ChatResponse>): List<Chat> =
        list.map {
            Chat(
                senderId = it.senderId ?: "",
                receiverId = it.receiverId ?: "",
                dateTimeSend = it.dateTimeSend ?: "",
                message = it.message ?: ""
            )
        }

    fun mapLocationResponseToDomain(list: List<DesaResponse>): List<Desa> =
        list.map {
            Desa(
                name = it.name ?: "",
                progress = it.progress ?: 0,
                latitude = it.latitude ?: 0.0,
                longitude = it.longitude ?: 0.0
            )
        }

    fun mapUserResponseToDomain(userResponse: UserResponse): User =
        User(
            id = userResponse.id ?: "",
            name = userResponse.name ?: "",
            email = userResponse.email ?: ""
        )
}