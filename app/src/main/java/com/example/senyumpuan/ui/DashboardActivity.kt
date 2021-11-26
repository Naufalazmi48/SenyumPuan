package com.example.senyumpuan.ui

import android.os.Bundle
import com.example.senyumpuan.databinding.ActivityDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}