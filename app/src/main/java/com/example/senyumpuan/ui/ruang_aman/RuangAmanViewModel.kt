package com.example.senyumpuan.ui.ruang_aman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Chat
import com.example.core.domain.model.User
import com.example.core.domain.usecase.chat.ChatUseCase
import com.example.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RuangAmanViewModel(
    private val userUseCase: UserUseCase,
    private val chatUseCase: ChatUseCase
) : ViewModel() {

    init {
        getDataSender()
    }
    var receiverId: String? = null


    private val _user = MutableLiveData<User>()
    val user:LiveData<User> = _user

    fun getDataSender() {
        viewModelScope.launch {
            userUseCase.getUser().collect {
                it.data?.let { mUser ->
                    _user.postValue(mUser)
                }
            }
        }
    }

    fun sendChat(chat: Chat) {
        viewModelScope.launch {
            chatUseCase.sendChat(chat)
        }
    }

    private val _chats = MutableLiveData<Resource<List<Chat>>>()
    val chats = _chats

    fun getChats(userId:String){
        viewModelScope.launch {
            chatUseCase.getChats(userId).collect {
                _chats.postValue(it)
            }
        }
    }
}