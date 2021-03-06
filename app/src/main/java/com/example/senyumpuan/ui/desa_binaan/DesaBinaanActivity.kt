package com.example.senyumpuan.ui.desa_binaan

import android.content.Intent
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityDesaBinaanBinding
import com.example.senyumpuan.ui.BaseActivity
import com.example.senyumpuan.ui.desa_binaan.DetailDesaBinaanFragment.Companion.DETAIL_DATA
import com.example.senyumpuan.ui.desa_binaan.DetailDesaBinaanFragment.Companion.TAG
import com.example.senyumpuan.utils.Event
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class DesaBinaanActivity : BaseActivity<ActivityDesaBinaanBinding>() {

    private lateinit var mapBox: MapboxMap

    private val desaBinaanViewModel: DesaBinaanViewModel by viewModel()

    override fun getViewBinding(): ActivityDesaBinaanBinding =
        ActivityDesaBinaanBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.desa_binaan)

        intent.getStringExtra(ROLE_USER)?.let { role ->
            if (role.contains(getString(R.string.role_admin), true)) {
                binding.fabAddDesaBinaan.isVisible = true

                setupFabListener()
            }
        }

        binding.mapBox.getMapAsync { map ->
            this.mapBox = map

            desaBinaanViewModel.desa.observe(this, this::mapsObserver)
            desaBinaanViewModel.getLocationDesaBinaan()
        }
    }

    override fun onResume() {
        super.onResume()

        if (this::mapBox.isInitialized) {
            desaBinaanViewModel.getLocationDesaBinaan()
        }
    }

    private fun setupFabListener() {
        binding.fabAddDesaBinaan.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Class.forName("com.example.admin.ui.desa_binaan.AddDesaBinaanActivity")
                )
            )
        }
    }

    private fun mapsObserver(result: Event<Resource<List<Desa>>>) {
        result.getContentIfNotHandled()?.let { listDesa ->
            when (listDesa) {
                is Resource.Error -> {
                    binding.loading.isVisible = false
                    Snackbar.make(binding.root, "${listDesa.message}", Snackbar.LENGTH_SHORT)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
                }
                is Resource.Loading -> binding.loading.isVisible = true
                is Resource.Success -> {
                    binding.loading.isVisible = false
                    listDesa.data?.let {
                        showMarker(it)
                    }
                }
            }
        }
    }

    private fun showMarker(data: List<Desa>) {
        mapBox.setStyle(Style.MAPBOX_STREETS) { style ->
            ResourcesCompat.getDrawable(resources, R.drawable.ic_location, null)?.let {
                style.addImage(
                    ID_ICON,
                    it
                )
            }

            val symbolManager = SymbolManager(binding.mapBox, mapBox, style)
            symbolManager.iconAllowOverlap = true

            val options = ArrayList<SymbolOptions>()
            data.forEach {
                options.add(
                    SymbolOptions()
                        .withLatLng(LatLng(it.latitude, it.longitude))
                        .withIconImage(ID_ICON)
                        .withData(Gson().toJsonTree(it))
                )
            }
            symbolManager.create(options)

            symbolManager.addClickListener { symbol ->
                val desa = Gson().fromJson(symbol.data, Desa::class.java)
                val dialogFragment = DetailDesaBinaanFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(
                            DETAIL_DATA, desa
                        )
                    }
                }
                dialogFragment.show(supportFragmentManager, TAG)
            }
        }
    }

    companion object {
        const val ID_ICON = "ID_ICON"
        const val ROLE_USER = "ROLE_USER"
    }
}