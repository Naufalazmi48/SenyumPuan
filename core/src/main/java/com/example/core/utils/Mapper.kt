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
                dateTimeSend = it.dateTimeSend ?: 0L,
                message = it.message ?: "",
                senderRole = it.senderRole ?: "user"
            )
        }

    fun mapLocationResponseToDomain(list: List<DesaResponse>): List<Desa> =
        list.map {
            Desa(
                name = it.name ?: "",
                description = it.description ?: "",
                latitude = it.latitude ?: 0.0,
                longitude = it.longitude ?: 0.0,
                pictures = it.pictures ?: emptyList()
            )
        }

    fun mapUserResponseToDomain(userResponse: UserResponse): User =
        User(
            id = userResponse.id ?: "",
            name = userResponse.name ?: "",
            email = userResponse.email ?: "",
            address = userResponse.address ?: "",
            gender = userResponse.gender ?: "",
            age = userResponse.age ?: 0,
            phoneNumber = userResponse.phoneNumber ?: "",
            role = userResponse.role ?: "user"
        )
}