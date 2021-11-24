package com.example.senyumpuan.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.core.data.Resource
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivitySignInBinding
import com.example.senyumpuan.ui.BaseActivity
import com.example.senyumpuan.ui.MainActivity
import com.example.senyumpuan.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
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
        binding.email.text.toString().isNotEmpty() && binding.password.text.toString().isNotEmpty()

    override fun onClick(v: View) {
        when(v.id) {
            R.id.sign_in -> {
                if (validateForm()) {
                    val email = binding.email.text.toString()
                    val password = binding.password.text.toString()
                    viewModel.login(email, password)
                } else {
                    if (binding.email.text.toString().isEmpty()){
                        binding.email.error = "Email tidak boleh kosong"
                    }
                    if(binding.password.text.toString().isEmpty()){
                        binding.password.error ="Password tidak boleh kosong"
                    }
                }
            }

            R.id.to_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun loginObserver(result: Resource<Boolean>){
        when (result){
            is Resource.Error -> {
                Snackbar.make( binding.root,"Email atau Password Salah", Snackbar.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }
            is Resource.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Resource.Success -> {
                binding.progressBar.isVisible = false

                val intent = Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

}