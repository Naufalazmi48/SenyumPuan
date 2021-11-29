package com.example.core.domain.usecase.user

import com.example.core.data.Resource
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import com.example.core.utils.NEED_VERIFICATION_EMAIL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun login(email: String, password: String): Flow<Resource<Boolean>> = flow {
        userRepository.loginUser(email, password).collect {
            when(it) {
                is Resource.Success -> {
                    if (!userRepository.isVerifiedEmail()) {
                        emit(Resource.Error<Boolean>(NEED_VERIFICATION_EMAIL))
                    } else {
                        emit(it)
                    }
                }
                else -> emit(it)
            }
        }
    }

    override fun register(user: User, password: String): Flow<Resource<Boolean>> = flow {
        userRepository.registerUser(user.email, password).collect {
                when(it){
                    is Resource.Success -> {
                        userRepository.sendEmailVerification()

                        userRepository.insertUser(user).collect { result ->
                            emit(result)
                        }
                    }

                    else -> emit(it)
                }
        }
    }

    override fun isLogginedUser(): Boolean = userRepository.isLogginedUser() && userRepository.isVerifiedEmail()

    override fun getUser(userId: String?): Flow<Resource<User>> = userRepository.getUser(userId)
}