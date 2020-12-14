package com.test.testtdlib.presentation.sign_in

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.test.testtdlib.data.AuthorizationState
import com.test.testtdlib.data.TelegramClient
import com.test.testtdlib.navigation.Screens
import com.test.testtdlib.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Named

class SignInViewModel @Inject constructor(
    @Named("Local") private val localRouter: Router,
    private val telegramClient: TelegramClient
) : BaseViewModel() {


    init {
        telegramClient.authorizationStatus
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                Log.d("Test", "$state")
                when (state) {
                    AuthorizationState.PHONE_NUMBER -> {
                        localRouter.newRootChain(Screens.PhoneNumber())
                    }
                    AuthorizationState.AUTHENTICATION_CODE -> {
                        localRouter.navigateTo(Screens.AuthenticationCode())
                    }
                    AuthorizationState.FIRST_LAST_NAME -> {
                        localRouter.navigateTo(Screens.FirstLastName())
                    }
                    AuthorizationState.PASSWORD -> {
                        localRouter.navigateTo(Screens.Password())
                    }
                    else -> {}
                }
            }
    }

    fun sendPhoneNumber(phoneNumber: String) {
        telegramClient.sendPhoneNumber(phoneNumber)
    }

    fun sendAuthenticationCode(code: String) {
        telegramClient.sendAuthenticationCode(code)
    }

    fun sendFirstLastName(firstName: String, lastName: String) {
        telegramClient.sendFirstLastName(firstName, lastName)
    }

    fun sendPassword(password: String) {
        telegramClient.sendPassword(password)
    }

}