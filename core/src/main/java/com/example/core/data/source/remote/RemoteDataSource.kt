package com.example.core.data.source.remote

import android.net.Uri
import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.ChatResponse
import com.example.core.data.source.remote.response.DesaResponse
import com.example.core.data.source.remote.response.UserResponse
import com.example.core.domain.model.Chat
import com.example.core.domain.model.Desa
import com.example.core.domain.model.User
import com.example.core.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

@ExperimentalCoroutinesApi
class RemoteDataSource(
    private val mDbRef: DatabaseReference,
    private val mAuth: FirebaseAuth,
    private val mStorageRef: StorageReference,
    private val mFirestore: FirebaseFirestore
) {

    fun getChats(userId: String): Flow<ApiResponse<List<ChatResponse>>> = callbackFlow {
        mDbRef.child(CHATS_PATH).child(userId).child(MESSAGES_PATH).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val listChat = arrayListOf<ChatResponse>()
                    snapshot.children.forEach {
                        val chat = it.getValue(ChatResponse::class.java)
                        if (chat != null) {
                            listChat.add(chat)
                        }
                    }

                    if (listChat.isNullOrEmpty()) trySend(ApiResponse.Empty)
                    else trySend(ApiResponse.Success(listChat.toList()))
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG_FIREBASE, error.message)
                    trySend(ApiResponse.Error(FAILED_TO_CONNECT))
                }
            }
        )
        awaitClose { close() }
    }

    fun getChatAllUser(): Flow<ApiResponse<List<ChatResponse>>> = callbackFlow {
        mDbRef.child(CHATS_PATH).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val listChat = arrayListOf<ChatResponse>()

                    snapshot.children.forEach {
                        it.key?.let { key ->
                            val children = snapshot.child(key).child(MESSAGES_PATH).children.last()
                            val chat = children.getValue(ChatResponse::class.java)
                            chat?.let { message -> listChat.add(message) }
                        }
                    }

                    if (listChat.isNullOrEmpty()) trySend(ApiResponse.Empty)
                    else trySend(ApiResponse.Success(listChat.toList()))
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG_FIREBASE, error.message)
                    trySend(ApiResponse.Error(FAILED_TO_CONNECT))
                }
            }
        )
        awaitClose { close() }
    }

    fun sendChat(chat: Chat) {
        try {
            mDbRef.child(CHATS_PATH).child(chat.receiverId).child(MESSAGES_PATH).push()
                .setValue(chat)
        } catch (e: Exception) {
            Log.e(TAG_FIREBASE, "${e.message}")
        }
    }

    fun getLocationDesaBinaan(): Flow<ApiResponse<List<DesaResponse>>> = callbackFlow {

        mFirestore.collection(DESA_BINAAN_PATH).get().addOnSuccessListener { snapshot ->
            val listDesa = arrayListOf<DesaResponse>()
            snapshot.forEach { document ->
                val desa = document.toObject(DesaResponse::class.java)
                listDesa.add(desa)
            }

            if (listDesa.isNullOrEmpty()) trySend(ApiResponse.Empty)
            else trySend(ApiResponse.Success(listDesa.toList()))
        }.addOnFailureListener { error ->
            Log.e(TAG_FIREBASE, error.message.toString())
            trySend(ApiResponse.Error(FAILED_TO_CONNECT))
        }
        awaitClose { close() }
    }

    fun addLocationDesaBinaan(desa: Desa) {
        try {
            mFirestore.collection(DESA_BINAAN_PATH).document(UUID.randomUUID().toString()).set(desa)
        } catch (e: Exception) {
            Log.e(TAG_FIREBASE, "${e.message}")
        }
    }

    fun registerUser(email: String, password: String): Flow<ApiResponse<Boolean>> = callbackFlow {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(ApiResponse.Success(true))
            } else {
                trySend(ApiResponse.Error(FAILED_REGISTRATION))
                Log.e(TAG_FIREBASE, "${task.exception?.message}")
            }
        }
        awaitClose { close() }
    }

    fun loginUser(email: String, password: String): Flow<ApiResponse<Boolean>> = callbackFlow {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(ApiResponse.Success(true))
            } else {
                trySend(ApiResponse.Error(FAILED_AUTHENTICATION))
                Log.e(TAG_FIREBASE, "${task.exception?.message}")
            }
        }
        awaitClose { close() }
    }

    fun insertUser(user: User): Flow<ApiResponse<Boolean>> = callbackFlow {
        try {
            val userWithId = mAuth.currentUser?.let { user.copy(id = it.uid) }

            userWithId?.let { mFirestore.collection(USERS_PATH).document(it.id).set(userWithId) }
            trySend(ApiResponse.Success(true))
        } catch (e: Exception) {
            trySend(ApiResponse.Error(FAILED_TO_INSERT))
            Log.e(TAG_FIREBASE, "${e.message}")
        }
        awaitClose { close() }
    }

    fun getUser(userId: String?): Flow<ApiResponse<UserResponse>> = callbackFlow {
        val id = userId ?: mAuth.uid
        mFirestore.collection(USERS_PATH).document(id.toString()).get()
            .addOnSuccessListener { snapshot ->
                val data = snapshot.toObject(UserResponse::class.java)

                if (data != null) trySend(ApiResponse.Success(data))
                else trySend(ApiResponse.Empty)
            }
            .addOnFailureListener { error ->
                Log.e(TAG_FIREBASE, error.message.toString())
                trySend(ApiResponse.Error(FAILED_AUTHENTICATION))
            }
        awaitClose { close() }
    }

    fun isLogginedUser(): Boolean = mAuth.currentUser != null

    fun isVerifiedEmail(): Boolean = mAuth.currentUser?.isEmailVerified ?: false

    fun sendEmailVerification() {
        mAuth.currentUser?.sendEmailVerification()
    }

    fun signOut(): Boolean = try {
        mAuth.signOut()
        true
    } catch (e: Exception) {
        Log.e(TAG_FIREBASE, e.message ?: "")
        false
    }

    fun uploadImage(villageName: String, uri: Uri): Flow<ApiResponse<String>> =
        callbackFlow {
            try {
                val ref = mStorageRef.child(villageName).child(IMAGES_PATH)
                    .child(UUID.randomUUID().toString())
                ref.putFile(uri)
                    .addOnCompleteListener {
                        ref.downloadUrl.addOnSuccessListener { uri ->
                            trySend(ApiResponse.Success(uri.toString()))
                        }
                    }
                    .addOnFailureListener { error ->
                        Log.e(TAG_FIREBASE, error.message.toString())
                        trySend(ApiResponse.Error(FAILED_UPLOAD_IMAGE))
                    }
            } catch (e: Exception) {
                print(e.message)
            }

            awaitClose { close() }
        }

    companion object {
        private const val DESA_BINAAN_PATH = "desa_binaan"
        private const val CHATS_PATH = "chats"
        private const val USERS_PATH = "users"
        private const val MESSAGES_PATH = "messages"
        private const val IMAGES_PATH = "images"
    }
}