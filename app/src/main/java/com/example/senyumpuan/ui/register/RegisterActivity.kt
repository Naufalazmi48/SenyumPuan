package com.example.senyumpuan.ui.register

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.core.data.Resource
import com.example.core.domain.model.User
import com.example.core.utils.Helper.formErrorHandler
import com.example.core.utils.Helper.setAutoClearError
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityRegisterBinding
import com.example.senyumpuan.ui.BaseActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(), View.OnClickListener {

    private val viewModel: RegisterViewModel by viewModel()

    override fun getViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListener()
        setupAdapter()

        viewModel.register.observe(this, this::registerObserver)
    }

    private fun setupAdapter() {
        val items = resources.getStringArray(R.array.list_gender)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items)
        binding.dropGender.setAdapter(adapter)
    }

    private fun registerObserver(result: Resource<Boolean>) {
        when (result) {
            is Resource.Error -> {
                Snackbar.make(binding.root, "${result.message}", Snackbar.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }
            is Resource.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Resource.Success -> {
                binding.progressBar.isVisible = false
                Toast.makeText(this, getString(R.string.success_regiter), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            register.setOnClickListener(this@RegisterActivity)

            edtName.setAutoClearError(name)
            edtEmail.setAutoClearError(email)
            dropGender.setAutoClearError(gender)
            edtPhone.setAutoClearError(phone)
            edtAddress.setAutoClearError(address)
            edtAge.setAutoClearError(age)
            edtPassword.setAutoClearError(password)
        }
    }

    private fun validateForm(): Boolean =
        binding.edtName.text.toString().isNotEmpty() &&
                binding.edtEmail.text.toString().isNotEmpty() &&
                binding.dropGender.text.toString().isNotEmpty() &&
                binding.edtPhone.text.toString().isNotEmpty() &&
                binding.edtAddress.text.toString().isNotEmpty() &&
                binding.edtAge.text.toString().isNotEmpty() &&
                binding.edtPassword.text.toString().isNotEmpty() &&
                binding.edtPassword.text.toString().trim().length >= 6


    override fun onClick(v: View) {
        when (v.id) {
            R.id.register -> {
                if (validateForm()) {
                    val user = getUserForm()
                    val password = binding.edtPassword.text.toString()

                    viewModel.register(user, password)
                } else {
                    showErrorMessage()
                }
            }
        }
    }

    private fun getUserForm(): User {
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val gender = binding.dropGender.text.toString()
        val phone = binding.edtPhone.text.toString()
        val address = binding.edtAddress.text.toString()
        val birth = binding.edtAge.text.toString()

        return User(
            id = "",
            email = email,
            name = name,
            gender = gender,
            address = address,
            age = birth.toInt(),
            phoneNumber = phone
        )
    }

    private fun showErrorMessage() {
        with(binding) {
            formErrorHandler(name, edtName.text.toString().isEmpty(), getString(R.string.empty_name))
            formErrorHandler(email, edtEmail.text.toString().isEmpty(), getString(R.string.empty_email))
            formErrorHandler(gender, dropGender.text.toString().isEmpty(), getString(R.string.empty_gender))
            formErrorHandler(phone, edtPhone.text.toString().isEmpty(), getString(R.string.empty_hp))
            formErrorHandler(address, edtAddress.text.toString().isEmpty(), getString(R.string.empty_address))
            formErrorHandler(
                age,
                edtAge.text.toString().isEmpty(),
                getString(R.string.empty_age)
            )
            formErrorHandler(
                password,
                edtPassword.text.toString().isEmpty(),
                getString(R.string.empty_password)
            )
            formErrorHandler(
                password,
                edtPassword.text.toString().isNotEmpty() && edtPassword.text.toString()
                    .trim().length < 6,
                getString(R.string.password_must_six_digit)
            )
        }
    }
}