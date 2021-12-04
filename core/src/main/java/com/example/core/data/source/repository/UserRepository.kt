package com.example.core.data.source.repository

import com.example.core.data.Resource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import com.example.core.utils.FAILED_AUTHENTICATION
import com.example.core.utils.FAILED_REGISTRATION
import com.example.core.utils.Mapper.mapUserResponseToDomain
import com.example.core.utils.NEED_CONNECTION
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class UserRepository(private val remoteDataSource: RemoteDataSource): IUserRepository {
    override fun registerUser(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        remoteDataSource.registerUser(email, password).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Error<Boolean>(FAILED_REGISTRATION))
                is ApiResponse.Error -> emit(Resource.Error<Boolean>(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(true))
            }
        }
    }

    override fun loginUser(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        remoteDataSource.loginUser(email, password).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Error<Boolean>(FAILED_AUTHENTICATION))
                is ApiResponse.Error -> emit(Resource.Error<Boolean>(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(it.data))
            }
        }
    }

    override fun insertUser(user: User): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        remoteDataSource.insertUser(user).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Success(false))
                is ApiResponse.Error -> emit(Resource.Error<Boolean>(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(true))
            }
        }
    }

    override fun getUser(userId: String?): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        remoteDataSource.getUser(userId).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Error<User>(NEED_CONNECTION))
                is ApiResponse.Error -> emit(Resource.Error<User>(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success<User>(mapUserResponseToDomain(it.data)))
            }
        }
    }

    override fun isLogginedUser(): Boolean = remoteDataSource.isLogginedUser()
    override fun isVerifiedEmail(): Boolean = remoteDataSource.isVerifiedEmail()

    override fun sendEmailVerification() {
        remoteDataSource.sendEmailVerification()
    }

    override fun signOut(): Boolean = remoteDataSource.signOut()
}