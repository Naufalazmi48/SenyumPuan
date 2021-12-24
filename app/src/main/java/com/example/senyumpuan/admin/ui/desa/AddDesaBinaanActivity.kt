package com.example.senyumpuan.admin.ui.desa

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityAddDesaBinaanBinding
import com.example.senyumpuan.ui.BaseActivity
import com.example.senyumpuan.utils.Helper.formErrorHandler
import com.example.senyumpuan.utils.Helper.setAutoClearError
import com.example.senyumpuan.utils.Helper.setScrollable
import org.koin.android.ext.android.inject

class AddDesaBinaanActivity : BaseActivity<ActivityAddDesaBinaanBinding>(), View.OnClickListener {

    private val viewModel: AddDesaBinaanViewModel by inject()
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.imagesUri.value = it }
        }
    private lateinit var mAdapter: ImageAdapter


    override fun getViewBinding(): ActivityAddDesaBinaanBinding =
        ActivityAddDesaBinaanBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        mAdapter = ImageAdapter(this::onAddImageAction)

        setupRecyclerView()
        setupListener()
        viewModel.imagesUri.observe(this, this::onAddNewImage)
        viewModel.desa.observe(this, this::observerDesa)
    }

    private fun onAddImageAction() {
        selectImageFromGalleryResult.launch("image/*")
    }

    private fun observerDesa(resource: Resource<Boolean>) {
        with(binding) {
            when (resource) {
                is Resource.Error -> {
                    loading.isVisible = false
                    Toast.makeText(
                        this@AddDesaBinaanActivity,
                        getString(R.string.failed_add_desa),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> loading.isVisible = true
                is Resource.Success -> {
                    loading.isVisible = false

                    Toast.makeText(
                        this@AddDesaBinaanActivity,
                        getString(R.string.success_add_desa),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun onAddNewImage(uri: Uri) {
        mAdapter.addImageUri(uri)
    }

    private fun setupRecyclerView() {
        with(binding.rvImage) {
            layoutManager = LinearLayoutManager(
                this@AddDesaBinaanActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = mAdapter
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListener() {
        with(binding) {

            btnAddVillage.setOnClickListener(this@AddDesaBinaanActivity)
            edtSummary.setScrollable()

            edtName.setAutoClearError(name)
            edtLatitude.setAutoClearError(latitude)
            edtLongitude.setAutoClearError(longitude)
            edtSummary.setAutoClearError(summary)
        }
    }

    private fun showErrorMessage() {
        with(binding) {
            formErrorHandler(
                name,
                edtName.text.toString().isEmpty(),
                getString(R.string.empty_village_name)
            )
            formErrorHandler(
                latitude,
                edtLatitude.text.toString().isEmpty(),
                getString(R.string.empty_latitude)
            )
            formErrorHandler(
                longitude,
                edtLongitude.text.toString().isEmpty(),
                getString(R.string.empty_longitude)
            )
            formErrorHandler(
                summary,
                edtSummary.text.toString().isEmpty(),
                getString(R.string.empty_desc_village)
            )

            if (mAdapter.getData().isEmpty()) {
                Toast.makeText(
                    this@AddDesaBinaanActivity,
                    getString(R.string.empty_documentation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isValidForm(): Boolean =
        binding.edtName.text.toString().isNotEmpty() &&
                binding.edtLatitude.text.toString().isNotEmpty() &&
                binding.edtLongitude.text.toString().isNotEmpty() &&
                binding.edtSummary.text.toString().isNotEmpty() &&
                mAdapter.getData().isNotEmpty()

    private fun getForm(): Desa {
        with(binding) {
            return Desa(
                name = edtName.text.toString(),
                description = edtSummary.text.toString(),
                latitude = edtLatitude.text.toString().toDouble(),
                longitude = edtLongitude.text.toString().toDouble(),
                pictures = mAdapter.getData()
            )
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_add_village) {
            if (isValidForm()) {
                viewModel.addLocationDesa(getForm())
            } else {
                showErrorMessage()
            }
        }
    }


}