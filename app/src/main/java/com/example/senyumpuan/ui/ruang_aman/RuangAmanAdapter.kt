package com.example.senyumpuan.ui.ruang_aman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Chat
import com.example.core.utils.getStringTime
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ItemReceiveChatBinding
import com.example.senyumpuan.databinding.ItemSentMessageBinding

class RuangAmanAdapter (private val senderRole: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listChat = arrayListOf<Chat>()

    fun setData(listChat: List<Chat>){
        this.listChat.clear()
        this.listChat.addAll(listChat)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == RECEIVER_ID) {
            ReceiveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_receive_chat, parent, false))
        } else {
            SentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sent_message, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = listChat[position]
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            viewHolder.tvSendMessage.text = chat.message.trim()
            viewHolder.tvSendTime.text = getStringTime(chat.dateTimeSend)
        } else {
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.tvReceiveMessage.text = chat.message.trim()
            viewHolder.tvReceiveTime.text = getStringTime(chat.dateTimeSend)
        }
    }

    override fun getItemCount(): Int  = listChat.size

    override fun getItemViewType(position: Int): Int {
        return if(listChat[position].senderRole != senderRole) RECEIVER_ID else SENDER_ID
    }

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemSentMessageBinding.bind(itemView)
        val tvSendMessage = binding.textMessageOutcoming
        val tvSendTime = binding.textMessageOutcomingTime
    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemReceiveChatBinding.bind(itemView)
        val tvReceiveMessage = binding.textMessageIncoming
        val tvReceiveTime = binding.textMessageIncomingTime
    }

    companion object {
        private const val SENDER_ID = 1
        private const val RECEIVER_ID = 0

    }
}