package com.example.senyumpuan.ui.desa_binaan

import android.os.Bundle
import com.example.senyumpuan.databinding.ActivityDesaBinaanBinding
import com.example.senyumpuan.ui.BaseActivity

class DesaBinaanActivity : BaseActivity<ActivityDesaBinaanBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewBinding(): ActivityDesaBinaanBinding =
        ActivityDesaBinaanBinding.inflate(layoutInflater)
}