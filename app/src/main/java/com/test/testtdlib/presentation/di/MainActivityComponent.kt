package com.test.testtdlib.presentation.di

import com.test.testtdlib.presentation.MainActivity
import com.test.testtdlib.presentation.main.di.MainComponent
import com.test.testtdlib.presentation.sign_in.di.SignInComponent
import dagger.Subcomponent

@Subcomponent(modules = [
    MainActivityModule::class
])
interface MainActivityComponent {

    fun plusSignInComponent(): SignInComponent

    fun plusMainComponent(): MainComponent

    fun inject(activity: MainActivity)
}