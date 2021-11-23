package com.example.senyumpuan.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.core.data.Resource
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivitySignInBinding
import com.example.senyumpuan.ui.BaseActivity
import com.example.senyumpuan.ui.MainActivity
import com.example.senyumpuan.ui.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity<ActivitySignInBinding>(), View.OnClickListener {

    private val viewModel: SignInViewModel by viewModel()

    override fun getViewBinding(): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListener()
        viewModel.login.observe(this, this::loginObserver)
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
                    val email = binding.email.text.toString()
                    val password = binding.password.text.toString()
                    viewModel.login(email, password)
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

    private fun loginObserver(result: Resource<Boolean>){
        when (result){
            is Resource.Error -> {
//                TODO : Tampilkan pesan error dan non aktifkan animasi loading
            }
            is Resource.Loading -> {
//                 TODO : Aktifkan tampilan loading di sini
            }
            is Resource.Success -> {
//                TODO : Non aktifkan tampilan loading

                val intent = Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intent)
            }
        }
    }

}