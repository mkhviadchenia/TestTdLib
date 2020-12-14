package com.test.testtdlib.presentation.main.chat

import com.test.testtdlib.data.TelegramClient
import com.test.testtdlib.presentation.base.BaseViewModel
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val telegramClient: TelegramClient
): BaseViewModel() {

    private var chatID: Long = 0

    fun init(chatID: Long) {
        this.chatID = chatID
        telegramClient.getMessages(chatID)
    }

    fun sendMessage(message: String) {
        telegramClient.sendMessage(chatID, message)
    }
}