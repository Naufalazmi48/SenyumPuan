package com.example.senyumpuan.admin.ui.ruang_aman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.usecase.chat.ChatUseCase
import com.example.core.presentation.model.UserWithChat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListChatViewModel(private val chatUseCase: ChatUseCase): ViewModel() {
    private val _listUser = MutableLiveData<Resource<List<UserWithChat>>>()
    val listUser: LiveData<Resource<List<UserWithChat>>> = _listUser

    fun getChatAllUser(){
        viewModelScope.launch {
            chatUseCase.getChatAllUser().collect {
                _listUser.postValue(it)
            }
        }
    }
}