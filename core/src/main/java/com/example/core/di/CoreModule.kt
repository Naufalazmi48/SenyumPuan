package com.example.core.di

import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.repository.ChatRepository
import com.example.core.data.source.repository.MapRepository
import com.example.core.data.source.repository.UserRepository
import com.example.core.domain.repository.IChatRepository
import com.example.core.domain.repository.IMapsRepository
import com.example.core.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseDatabase.getInstance().reference }
    single { FirebaseStorage.getInstance().reference }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
}

@ExperimentalCoroutinesApi
val repositoryModule = module {
    single { RemoteDataSource(get(), get(), get(), get()) }
    single<IChatRepository> { ChatRepository(get()) }
    single<IMapsRepository> { MapRepository(get()) }
    single<IUserRepository> { UserRepository(get()) }
}