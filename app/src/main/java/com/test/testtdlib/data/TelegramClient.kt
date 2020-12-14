package com.test.testtdlib.data

import android.content.Context
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import java.io.File
import javax.inject.Inject


class TelegramClient @Inject constructor(private val context: Context) {

    companion object {
        private const val TAG = "TelegramClient"

        private const val API_ID = 2922656
        private const val API_HASH = "923a86471a2f88ebde0588042ef10a6f"
    }

    val haveAuthorization: BehaviorSubject<Boolean> = BehaviorSubject.create()
    val authorizationStatus: BehaviorSubject<AuthorizationState> = BehaviorSubject.create()

    private val client: Client = Client.create(UpdateHandler(), null, null)

    private inner class UpdateHandler : Client.ResultHandler {
        override fun onResult(obj: TdApi.Object) {
            Log.d(TAG, "UpdateHandler")
            Log.d(TAG, obj.javaClass.simpleName)
            when (obj.constructor) {
                TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                    val state = obj as TdApi.UpdateAuthorizationState
                    onAuthorizationStateUpdated(state.authorizationState)
                }
//TODO
                TdApi.UpdateChatReadInbox.CONSTRUCTOR -> {
                    val updateChat = obj as TdApi.UpdateChatReadInbox
                    val unreadCount = updateChat.unreadCount
                    Log.d(TAG, "chat id: ${updateChat.chatId}")
                    Log.d(TAG, "unread count: $unreadCount")
                }
            }
            Log.d(TAG, "************************")
        }

        private fun onAuthorizationStateUpdated(authorizationState: TdApi.AuthorizationState) {
            Log.d(TAG, "authorizationState $authorizationState")
            Log.d(TAG, "thread name: ${Thread.currentThread().name}")
            when (authorizationState.constructor) {
                TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
                    val directory = File(context.filesDir, "tdlib")
                    val parameters = TdApi.TdlibParameters()
                    parameters.databaseDirectory = directory.absolutePath
                    parameters.useMessageDatabase = true
                    parameters.useSecretChats = true
                    parameters.apiId = API_ID
                    parameters.apiHash = API_HASH
                    parameters.systemLanguageCode = "en"
                    parameters.deviceModel = "mobile"
                    parameters.applicationVersion = "1.0"
                    parameters.enableStorageOptimizer = true

                    client.send(
                        TdApi.SetTdlibParameters(parameters),
                        AuthorizationRequestHandler()
                    )
                }
                TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR -> {
                    client.send(
                        TdApi.CheckDatabaseEncryptionKey(),
                        AuthorizationRequestHandler()
                    )
                }
                TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
//                    TODO
                    Log.d(TAG, "phone number")
                    haveAuthorization.onNext(false)
                    authorizationStatus.onNext(AuthorizationState.PHONE_NUMBER)
                }
                TdApi.AuthorizationStateWaitOtherDeviceConfirmation.CONSTRUCTOR -> {
//                    TODO

                }
                TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                    authorizationStatus.onNext(AuthorizationState.AUTHENTICATION_CODE)
                }
                TdApi.AuthorizationStateWaitRegistration.CONSTRUCTOR -> {
                    authorizationStatus.onNext(AuthorizationState.FIRST_LAST_NAME)
                }
                TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                    authorizationStatus.onNext(AuthorizationState.PASSWORD)
                }
                TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                    haveAuthorization.onNext(true)
                }
                TdApi.AuthorizationStateLoggingOut.CONSTRUCTOR -> {
                    haveAuthorization.onNext(false)
                }
                TdApi.AuthorizationStateClosing.CONSTRUCTOR -> {
                    haveAuthorization.onNext(false)
                }
                TdApi.AuthorizationStateClosed.CONSTRUCTOR -> {

                }
                else -> {
                    Log.d(TAG, "Unsupported authorization state: $authorizationState")
                }
            }

        }
    }

    private class AuthorizationRequestHandler(callback: () -> Unit = {}) : Client.ResultHandler {
        override fun onResult(obj: TdApi.Object) {
            Log.d(TAG, "AuthorizationRequestHandler")
            Log.d(TAG, obj.javaClass.simpleName)
            when (obj.constructor) {
                TdApi.Error.CONSTRUCTOR -> {
                    val error = obj as TdApi.Error
                    Log.d(TAG, "message: ${error.message}")
                    Log.d(TAG, "code: ${error.code}")
                }
                TdApi.Ok.CONSTRUCTOR -> {

                }
                else -> {
                    Log.d(TAG, "Receive wrong response from TDLib: $obj")
                }
            }
            Log.d(TAG, "************************")
        }
    }

    fun sendPhoneNumber(phoneNumber: String) {
        client.send(
            TdApi.SetAuthenticationPhoneNumber(phoneNumber, null),
            AuthorizationRequestHandler()
        )
    }

    fun sendAuthenticationCode(code: String) {
        client.send(TdApi.CheckAuthenticationCode(code), AuthorizationRequestHandler())
    }

    fun sendFirstLastName(firstName: String, lastName: String) {
        client.send(TdApi.RegisterUser(firstName, lastName), AuthorizationRequestHandler())
    }

    fun sendPassword(password: String) {
        client.send(TdApi.CheckAuthenticationPassword(password), AuthorizationRequestHandler())
    }


    fun sendMessage(chatID: Long, message: String) {
        val row = arrayOf(
            TdApi.InlineKeyboardButton(
                "https://telegram.org?1",
                TdApi.InlineKeyboardButtonTypeUrl()
            ),
            TdApi.InlineKeyboardButton(
                "https://telegram.org?2",
                TdApi.InlineKeyboardButtonTypeUrl()
            ),
            TdApi.InlineKeyboardButton(
                "https://telegram.org?3",
                TdApi.InlineKeyboardButtonTypeUrl()
            )
        )
        val replyMarkup = TdApi.ReplyMarkupInlineKeyboard(arrayOf(row, row, row))

        val content = TdApi.InputMessageText(TdApi.FormattedText(message, null), false, true)
        client.send(TdApi.SendMessage(chatID, 0, 0, null, replyMarkup, content), DefaultHandler())
    }

    fun getUserContacts(): Single<List<TdApi.User>> {
        return Observable.create<IntArray> {
            client.send(TdApi.GetContacts(), object : Client.ResultHandler {
                override fun onResult(obj: TdApi.Object) {
                    Log.d(TAG, "GetContacts")
                    Log.d(TAG, "$obj")
                    val user = obj as TdApi.Users
                    it.onNext(user.userIds)
                    it.onComplete()
                }
            })
        }.flatMap { userIDs ->
            return@flatMap Observable.fromIterable(userIDs.asIterable())
        }.flatMap { userID ->
            return@flatMap Observable.create<TdApi.User> {
                client.send(TdApi.GetUser(userID), object : Client.ResultHandler {
                    override fun onResult(obj: TdApi.Object) {
                        Log.d(TAG, "GetUserInfo")
                        Log.d(TAG, "$obj")
                        val user = obj as TdApi.User
                        it.onNext(user)
                        it.onComplete()
                    }
                })
            }
        }.toList()
    }

    fun openPrivateChat(chatID: Int): Single<TdApi.Chat> {
        return Single.create {
            client.send(
                TdApi.CreatePrivateChat(chatID.toInt(), true),
                object : Client.ResultHandler {
                    override fun onResult(obj: TdApi.Object) {
                        Log.d(TAG, "OpenPrivateChat")
                        Log.d(TAG, "$obj")
                        val chat = obj as TdApi.Chat
                        it.onSuccess(chat)
                    }
                })
        }
    }


    fun importContactTest(): Single<List<TdApi.User>> {
        return Single.create<Any> {
            val contacts = ContactUtils.readContacts(context.contentResolver)
                .map { TdApi.Contact(it.phoneNumber, it.firstName, it.lastName, "", 0) }
                .toTypedArray()
            client.send(TdApi.ImportContacts(contacts), object: Client.ResultHandler {
                override fun onResult(obj: TdApi.Object) {
                    Log.d(TAG, "ImportContacts")
                    it.onSuccess(true)
                }
            })
        }.flatMap{
            return@flatMap getUserContacts()
        }


    }

    fun getMessages(chatID: Long) {
        val fromMessage = 30408704L
        val offset = -99
        val limit = 100
        client.send(TdApi.GetChatHistory(chatID, fromMessage, offset, limit, false), object: Client.ResultHandler {
            override fun onResult(obj: TdApi.Object) {
                Log.d(TAG, "GetChatHistory")
                Log.d(TAG, "$obj")
            }
        })
    }

    private class DefaultHandler : Client.ResultHandler {
        override fun onResult(obj: TdApi.Object?) {
            Log.d(TAG, "DefaultHandler")
            Log.d(TAG, "$obj")

        }
    }

    private inner class ImportContactsHandler : Client.ResultHandler {
        override fun onResult(obj: TdApi.Object) {
            Log.d(TAG, "ImportContacts")
            Log.d(TAG, "$obj")
            when (obj.constructor) {
                TdApi.ImportedContacts.CONSTRUCTOR -> {
                   getUserContacts()
                }
            }
        }
    }


}