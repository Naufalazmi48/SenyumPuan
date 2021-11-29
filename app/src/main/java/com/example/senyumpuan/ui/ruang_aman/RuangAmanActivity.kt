package com.example.senyumpuan.ui.ruang_aman

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senyumpuan.databinding.ActivityRuangAmanBinding
import com.example.senyumpuan.ui.BaseActivity

class RuangAmanActivity : BaseActivity<ActivityRuangAmanBinding>() {

    private lateinit var mAdapter: RuangAmanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = RuangAmanAdapter("user")
        setupRecylerView()
    }

    private fun setupRecylerView() {
        with(binding.rvChat) {
            layoutManager = LinearLayoutManager(this@RuangAmanActivity)
            adapter = mAdapter
        }
    }



    override fun getViewBinding(): ActivityRuangAmanBinding =
        ActivityRuangAmanBinding.inflate(layoutInflater)
}