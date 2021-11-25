package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun registerUser(email: String, password: String): Flow<Resource<Boolean>>
    fun loginUser(email: String, password: String): Flow<Resource<Boolean>>
    fun insertUser(user:User): Flow<Resource<Boolean>>
    fun getUser(userId: String): Flow<Resource<User>>
    fun isLogginedUser():Boolean
    fun isVerifiedEmail():Boolean
    fun sendEmailVerification()
}