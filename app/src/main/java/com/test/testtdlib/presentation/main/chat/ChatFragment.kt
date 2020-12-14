package com.test.testtdlib.presentation.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.testtdlib.databinding.FragmentChatBinding
import com.test.testtdlib.di.Injector
import com.test.testtdlib.presentation.base.BaseFragment
import com.test.testtdlib.presentation.extensions.hideKeyboard
import com.test.testtdlib.presentation.main.chat.adapter.MessageAdapter

class ChatFragment() : BaseFragment<ChatViewModel>() {

    companion object {
        private const val ARG_CHAT_ID = "arg_chat_id"

        fun newInstance(chatID: Long): ChatFragment {
            val args = Bundle()
            args.putLong(ARG_CHAT_ID, chatID)
            val fragment = ChatFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentChatBinding
    private lateinit var adapter: MessageAdapter

    override fun inject() = Injector.mainComponent().inject(this)

    override fun injectViewModel() {
        viewModel = getViewModel()
        val chatID = requireArguments().getLong(ARG_CHAT_ID)
        viewModel.init(chatID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MessageAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonSend.setOnClickListener {
            val message = binding.fieldMessage.text.toString().trim()
            binding.fieldMessage.setText("")
            if (message.isNotEmpty()) {
                viewModel.sendMessage(message)
            }
            hideKeyboard()
        }

        binding.messagesListView.layoutManager = LinearLayoutManager(requireContext())
        binding.messagesListView.adapter = adapter
    }


}