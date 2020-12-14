package com.test.testtdlib.presentation

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.test.testtdlib.data.TelegramClient
import com.test.testtdlib.navigation.Screens
import com.test.testtdlib.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(
    @Named("Main") private val router: Router,
    private val telegramClient: TelegramClient
) : BaseViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        telegramClient.haveAuthorization
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { haveAuthorization ->
                Log.d("Test", "haveAuthorization $haveAuthorization")
                if (haveAuthorization) {
                    router.newRootScreen(Screens.Main())
                } else {
                    router.newRootScreen(Screens.SignIn())
                }
            }

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}