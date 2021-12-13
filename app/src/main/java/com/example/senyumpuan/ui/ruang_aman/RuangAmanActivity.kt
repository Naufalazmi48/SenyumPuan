package com.example.senyumpuan.ui.ruang_aman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.Chat
import com.example.core.domain.model.User
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityRuangAmanBinding
import com.example.senyumpuan.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RuangAmanActivity : BaseActivity<ActivityRuangAmanBinding>(), View.OnClickListener {

    private val viewModel: RuangAmanViewModel by viewModel()
    private var mAdapter: RuangAmanAdapter? = null
    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val text = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                binding.edtChat.append(text)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        supportActionBar?.title = getString(R.string.ruang_aman)
        intent.getStringExtra(GET_RECEIVER_ID)?.let { receiverId ->
            viewModel.receiverId = receiverId
        }

        setupListener()

        viewModel.user.observe(this, this::userObserver)
        viewModel.chats.observe(this, this::chatObserver)
    }

    private fun chatObserver(result: Resource<List<Chat>>) {
        when (result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                mAdapter?.setData(result.data ?: emptyList())
            }
        }
    }

    private fun userObserver(user: User) {
        mAdapter = RuangAmanAdapter(user.role)
        if (viewModel.receiverId == null) {
            viewModel.receiverId = user.id
        }

        setupRecylerView()
        viewModel.receiverId?.let {
            viewModel.getChats(it)
        }
    }

    private fun setupListener() {
        with(binding) {
            btnMicrophone.setOnClickListener(this@RuangAmanActivity)
            btnSend.setOnClickListener(this@RuangAmanActivity)
        }
    }

    private fun setupRecylerView() {
        with(binding.rvChat) {
            layoutManager = LinearLayoutManager(this@RuangAmanActivity)
            adapter = mAdapter
        }
    }

    override fun getViewBinding(): ActivityRuangAmanBinding =
        ActivityRuangAmanBinding.inflate(layoutInflater)

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_microphone -> {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                intent.putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_to_text_desc)
                )
                speechToText.launch(intent)
            }

            R.id.btn_send -> {
                val currentMessage = binding.edtChat.text.toString()
                val sender = viewModel.user.value
                val receiverId = viewModel.receiverId
                if (currentMessage.isNotEmpty() && (sender != null) && (receiverId != null)) {
                    val chat = Chat(
                        senderId = sender.id,
                        receiverId = receiverId,
                        dateTimeSend = Calendar.getInstance().timeInMillis,
                        message = currentMessage,
                        senderRole = sender.role
                    )
                    viewModel.sendChat(chat)
                    binding.edtChat.setText("")
                }
            }
        }
    }

    companion object {
        const val GET_RECEIVER_ID = "GET_RECEIVER_ID"
    }
}