package com.test.testtdlib.di

import com.test.testtdlib.App
import com.test.testtdlib.presentation.di.MainActivityComponent
import com.test.testtdlib.presentation.main.di.MainComponent
import com.test.testtdlib.presentation.sign_in.di.SignInComponent

object Injector {

    private lateinit var appComponent: AppComponent

    private var mainActivityComponent: MainActivityComponent? = null

    private var signInComponent: SignInComponent? = null

    private var mainComponent: MainComponent? = null

    fun init(application: App) {
        appComponent = DaggerAppComponent.factory().create(application)
    }

    fun mainActivityComponent(): MainActivityComponent {
        if (mainActivityComponent == null) {
            mainActivityComponent = appComponent.plusMainComponent()
        }
        return mainActivityComponent!!
    }

    fun clearMainActivityComponent() {
        mainActivityComponent = null
    }

    fun signInComponent(): SignInComponent {
        if (signInComponent == null) {
            signInComponent = mainActivityComponent().plusSignInComponent()
        }
        return signInComponent!!
    }

    fun clearSignInComponent() {
        signInComponent = null
    }

    fun mainComponent(): MainComponent {
        if (mainComponent == null) {
            mainComponent = mainActivityComponent().plusMainComponent()
        }
        return mainComponent!!
    }

    fun mainComponentClear() {
        mainComponent = null
    }
}