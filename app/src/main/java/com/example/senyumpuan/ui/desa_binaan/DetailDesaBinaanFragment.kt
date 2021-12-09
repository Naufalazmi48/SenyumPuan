package com.example.senyumpuan.ui.desa_binaan

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.domain.model.Desa
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.FragmentDetailDesaBinaanBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smarteist.autoimageslider.SliderView


class DetailDesaBinaanFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailDesaBinaanBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Desa>(DETAIL_DATA)?.let { desa ->

            setupScrollListener()

            with(binding) {
                villageName.text = desa.name
                villageAbout.text = desa.description
                villageAbout.movementMethod = ScrollingMovementMethod()
                imageSlider.apply {
                    val arrayListUrl = ArrayList<String>().apply { addAll(desa.pictures) }
                    setImageInSlider(arrayListUrl, this)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupScrollListener() {
        with(binding) {
            villageAbout.setOnTouchListener { _, _ ->
                villageAbout.parent.requestDisallowInterceptTouchEvent(true)
                false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDesaBinaanBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = SliderAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "BOTTOM_SHEET_TAG"
        const val DETAIL_DATA = "DETAIL_DATA"
    }

}