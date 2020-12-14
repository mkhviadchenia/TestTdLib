package com.test.testtdlib.presentation.main.chat

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.test.testtdlib.data.TelegramClient
import com.test.testtdlib.navigation.Screens
import com.test.testtdlib.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject
import javax.inject.Named

class ChatListViewModel @Inject constructor(
    @Named("Main") private val mainRouter: Router,
    private val telegramClient: TelegramClient
): BaseViewModel() {

    val contacts: MutableLiveData<List<TdApi.User>> = MutableLiveData(emptyList())


    fun getContacts() {
        telegramClient.getUserContacts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {  items ->
                contacts.value = items
            }
    }

    fun openChat(chatID: Int) {
        telegramClient.openPrivateChat(chatID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {chat ->
                    mainRouter.navigateTo(Screens.Chat(chat.id))
                },
                {

                }
            )
    }

    fun importContacts() {
        telegramClient.importContactTest()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {  items ->
                contacts.value = items
            }
    }

}