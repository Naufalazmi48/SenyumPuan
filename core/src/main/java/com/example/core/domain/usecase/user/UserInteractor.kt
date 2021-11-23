package com.example.core.domain.usecase.user

import com.example.core.data.Resource
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun login(email: String, password: String): Flow<Resource<Boolean>> =
        userRepository.loginUser(email, password)


    override fun register(user: User, password: String): Flow<Resource<Boolean>> = flow {
        userRepository.registerUser(user.email, password).collect {
                when(it){
                    is Resource.Error -> emit(it)
                    is Resource.Loading -> emit(it)
                    is Resource.Success -> {
                        userRepository.insertUser(user).collect { result ->
                            emit(result)
                        }
                    }
                }
        }
    }

    override fun isLogginedUser(): Boolean = userRepository.isLogginedUser()
}