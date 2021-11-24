package com.example.senyumpuan.ui.register

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import com.example.core.data.Resource
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityRegisterBinding
import com.example.senyumpuan.ui.BaseActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(), View.OnClickListener {

    override fun getViewBinding(): ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        val gender = resources.getStringArray(R.array.list_gender)
//        val arrayAdapter = ArrayAdapter(this, R.layout.list_item_gender, gender)
//        binding.dropGender.setAdapter(arrayAdapter)

        val items = listOf(R.array.list_gender)
        val adapter = ArrayAdapter(this, R.layout.list_item_gender, items)
        (binding.dropGender ).setAdapter(adapter)
    }

    private fun regisObserver(result: Resource<Boolean>){
        when (result){
            is Resource.Error -> {
                Snackbar.make( binding.root,"Gagal daftar", Snackbar.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }
            is Resource.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Resource.Success -> {
                binding.progressBar.isVisible = false
                Snackbar.make( binding.root,"Berhasil daftar", Snackbar.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupListener() {
        binding.register.setOnClickListener(this)
    }

    private fun validateForm(): Boolean =
        binding.edtName.text.toString().isNotEmpty() &&
                binding.edtEmail.text.toString().isNotEmpty() &&
                binding.dropGender.text.toString().isEmpty() &&
                binding.edtPhone.text.toString().isEmpty() &&
                binding.edtAddress.text.toString().isEmpty() &&
                binding.edtDateOfBirth.text.toString().isEmpty() &&
                binding.edtPassword.text.toString().isEmpty()

    override fun onClick(v: View) {
        when(v.id) {
            R.id.sign_in -> {
                if (validateForm()) {
                    val email = binding.edtEmail.text.toString()
                    val gender = binding.edtEmail.text.toString()
                    val phone = binding.edtEmail.text.toString()
                    val address = binding.edtEmail.text.toString()
                    val birth = binding.edtEmail.text.toString()
                    val password = binding.edtAddress.text.toString()
                   // viewModel.login(email, password)
                } else {
                    if (binding.edtName.text.toString().isEmpty()){
                        binding.edtName.error = "Nama tidak boleh kosong"
                    }
                    if(binding.edtEmail.text.toString().isEmpty()){
                        binding.edtEmail.error ="Email tidak boleh kosong"
                    }
                    if(binding.dropGender.text.toString().isEmpty()){
                        binding.dropGender.error ="Gender tidak boleh kosong"
                    }
                    if(binding.edtPhone.text.toString().isEmpty()){
                        binding.edtPhone.error ="Nomor Hp tidak boleh kosong"
                    }
                    if(binding.edtAddress.text.toString().isEmpty()){
                        binding.edtAddress.error ="Alamat tidak boleh kosong"
                    }
                    if(binding.edtDateOfBirth.text.toString().isEmpty()){
                        binding.edtDateOfBirth.error ="Tanggal tidak boleh kosong"
                    }
                    if(binding.edtPassword.text.toString().isEmpty()){
                        binding.edtPassword.error ="Password tidak boleh kosong"
                    }
                }
            }
        }
    }
}