package com.example.core.domain.usecase.user

import com.example.core.data.Resource
import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun login(email: String, password: String): Flow<Resource<Boolean>>
    fun register(user: User, password: String): Flow<Resource<Boolean>>
    fun isLogginedUser(): Boolean
    fun signOut(): Boolean
    fun getUser(userId: String? = null): Flow<Resource<User>>
}