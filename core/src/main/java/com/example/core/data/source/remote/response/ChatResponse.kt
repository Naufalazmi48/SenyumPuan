package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ChatResponse(

	@field:SerializedName("senderId")
	val senderId: String? = null,

	@field:SerializedName("receiverId")
	val receiverId: String? = null,

	@field:SerializedName("dateTimeSend")
	val dateTimeSend: Long? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("senderRole")
	val senderRole: String? = null,
)
