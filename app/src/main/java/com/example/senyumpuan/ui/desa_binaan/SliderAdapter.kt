package com.example.senyumpuan.ui.desa_binaan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.senyumpuan.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter:
    SliderViewAdapter<SliderAdapter.VH>() {
    private var mSliderItems = ArrayList<String>()
    fun renewItems(sliderItems: ArrayList<String>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.image_holder, parent, false)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        Glide.with(viewHolder.itemView)
            .load(mSliderItems[position])
            .centerCrop()
            .placeholder(R.drawable.ic_image_placeholder)
            .into(viewHolder.imageView)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)

    }
}