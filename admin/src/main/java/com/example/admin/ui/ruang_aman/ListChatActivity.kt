package com.example.admin.ui.ruang_aman

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senyumpuan.R
import com.example.admin.databinding.ActivityListChatBinding
import com.example.admin.di.adminViewModelModule
import com.example.core.data.Resource
import com.example.core.presentation.model.UserWithChat
import com.example.senyumpuan.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ListChatActivity : BaseActivity<ActivityListChatBinding>() {

    private val viewModel: ListChatViewModel by viewModel()
    private val mAdapter = ListChatAdapter()

    init {
        loadKoinModules(adminViewModelModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.ruang_aman)

        setupRecylerView()
        viewModel.listUser.observe(this, this::listUserObserver)
        viewModel.getChatAllUser()
    }

    private fun listUserObserver(result: Resource<List<UserWithChat>>) {
        when (result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                mAdapter.setData(result.data ?: emptyList())
            }
        }
    }

    private fun setupRecylerView() {
        with(binding.rvListChat){
            layoutManager = LinearLayoutManager(this@ListChatActivity)
            adapter = mAdapter
        }
    }

    override fun getViewBinding(): ActivityListChatBinding =
        ActivityListChatBinding.inflate(layoutInflater)

}