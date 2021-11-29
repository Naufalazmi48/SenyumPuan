package com.example.core.presentation.model

import com.example.core.domain.model.Chat
import com.example.core.domain.model.User

data class UserWithChat(
    var user: User,
    var chat: Chat
)
