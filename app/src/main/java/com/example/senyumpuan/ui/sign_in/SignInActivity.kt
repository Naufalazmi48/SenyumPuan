package com.example.senyumpuan.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.core.data.Resource
import com.example.core.utils.Helper.formErrorHandler
import com.example.core.utils.Helper.setAutoClearError
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivitySignInBinding
import com.example.senyumpuan.ui.BaseActivity
import com.example.senyumpuan.ui.DashboardActivity
import com.example.senyumpuan.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity<ActivitySignInBinding>(), View.OnClickListener {

    private val viewModel: SignInViewModel by viewModel()

    override fun getViewBinding(): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setupListener()
        viewModel.login.observe(this, this::loginObserver)
    }

    private fun setupListener() {
        with(binding){
            signIn.setOnClickListener(this@SignInActivity)
            toRegister.setOnClickListener(this@SignInActivity)
            edtEmail.setAutoClearError(email)
            edtPassword.setAutoClearError(password)
        }
    }

    private fun validateForm(): Boolean =
        binding.edtEmail.text.toString().isNotEmpty() && binding.edtPassword.text.toString().isNotEmpty()

    override fun onClick(v: View) {
        when(v.id) {
            R.id.sign_in -> {
                if (validateForm()) {
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()
                    viewModel.login(email, password)
                } else {
                    showErrorMessage()
                }
            }

            R.id.to_register -> {
                        startActivity(Intent(this, RegisterActivity::class.java))
            }

        }
    }

    private fun showErrorMessage() {
        with(binding){
           formErrorHandler(email, edtEmail.text.toString().isEmpty(), getString(R.string.empty_email))
           formErrorHandler(password, edtPassword.text.toString().isEmpty(), getString(R.string.empty_password))
        }
    }

    private fun loginObserver(result: Resource<Boolean>){
        when (result){
            is Resource.Error -> {
                Snackbar.make( binding.root,"${result.message}", Snackbar.LENGTH_SHORT)
                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                    .show()
                binding.progressBar.isVisible = false
            }
            is Resource.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Resource.Success -> {
                binding.progressBar.isVisible = false

                val intent = Intent(this, DashboardActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

}