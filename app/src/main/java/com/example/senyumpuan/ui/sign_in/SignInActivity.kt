package com.example.senyumpuan.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.senyumpuan.BaseActivity
import com.example.senyumpuan.MainActivity
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivitySignInBinding
import com.example.senyumpuan.ui.register.RegisterActivity

class SignInActivity : BaseActivity<ActivitySignInBinding>(), View.OnClickListener {
    override fun getViewBinding(): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListener()
    }

    private fun setupListener() {
        binding.signIn.setOnClickListener(this)
        binding.toRegister.setOnClickListener(this)
    }

    private fun validateForm(): Boolean =
        binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()

    override fun onClick(v: View) {
        var intent: Intent? = null
        when(v.id) {
            R.id.sign_in -> {
                if (validateForm()) {
                    // TODO if login success
                       intent = Intent(this@SignInActivity, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                } else {
                    // TODO invalid form action
                }
            }

            R.id.to_register -> {
                intent = Intent(this, RegisterActivity::class.java)
            }

        }

        intent?.let { startActivity(it) }
    }

}