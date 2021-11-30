package com.example.admin.ui.ruang_aman

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.databinding.ActivityListChatBinding
import com.example.senyumpuan.ui.BaseActivity

class ListChatActivity : BaseActivity<ActivityListChatBinding>() {

    private val mAdapter = ListChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setupRecylerView()
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