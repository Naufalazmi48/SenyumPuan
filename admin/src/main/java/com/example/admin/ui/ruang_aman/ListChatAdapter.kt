package com.example.admin.ui.ruang_aman

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.databinding.ItemChatBinding
import com.example.core.presentation.model.UserWithChat
import com.example.core.utils.getStringDate
import com.example.core.utils.getStringTime
import com.example.senyumpuan.ui.ruang_aman.RuangAmanActivity
import com.example.senyumpuan.ui.ruang_aman.RuangAmanActivity.Companion.GET_RECEIVER_ID

class ListChatAdapter : RecyclerView.Adapter<ListChatAdapter.ListChatViewHolder>() {

    private val listUser = arrayListOf<UserWithChat>()

    fun setData(listChat: List<UserWithChat>) {
        this.listUser.clear()
        this.listUser.addAll(listChat)
        notifyDataSetChanged()
    }

    inner class ListChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemChatBinding.bind(itemView)
        fun bind(userWithChat: UserWithChat) {
            with(binding) {
                userName.text = userWithChat.user.name
                lastMessage.text = userWithChat.chat.message
                time.text = getStringTime(userWithChat.chat.dateTimeSend)
                date.text = getStringDate(userWithChat.chat.dateTimeSend)

                root.setOnClickListener {
                    ContextCompat.startActivity(
                        itemView.context,
                        Intent(itemView.context, RuangAmanActivity::class.java).apply {
                            putExtra(GET_RECEIVER_ID, userWithChat.user.id)
                        },
                        null
                    )
                }
            }
        }
    }



    override fun onBindViewHolder(holder: ListChatViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListChatViewHolder =
        ListChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        )

}