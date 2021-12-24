package com.example.senyumpuan.admin.ui.desa

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ItemImageBinding
import kotlin.reflect.KFunction0

class ImageAdapter(private val onAddAction: KFunction0<Unit>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val listUri: ArrayList<Uri> = arrayListOf(Uri.EMPTY)

    fun addImageUri(uri: Uri) {
        listUri.add(uri)
        notifyItemInserted(listUri.size-1)
    }

    fun getData(): List<String> = listUri.filter { it != Uri.EMPTY }. map { it.toString() }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemImageBinding.bind(itemView)
        fun bind(uri: Uri) {
            with(binding) {
                Glide.with(root)
                    .load(uri)
                    .into(image)

                cancel.isVisible = true
                imageAdd.isVisible = false

                cancel.setOnClickListener {
                    val position = listUri.indexOf(uri)
                    listUri.remove(uri)
                    notifyItemRemoved(position)
                }
            }
        }

        fun default() {
            binding.root.setOnClickListener {
                onAddAction.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (listUri[position] == Uri.EMPTY) {
            holder.default()
        } else {
            holder.bind(listUri[position])
        }
    }

    override fun getItemCount(): Int = listUri.size
}