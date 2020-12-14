package com.test.testtdlib.presentation.sign_in.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.test.testtdlib.di.annatations.SignInScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SignInNavigationModule {

    private val ciceroneLocal: Cicerone<Router> = Cicerone.create()

    @SignInScope
    @Provides
    @Named("Local")
    fun provideLocalRouter(): Router = ciceroneLocal.router

    @SignInScope
    @Provides
    @Named("Local")
    fun provideLocalNavigationHolder(): NavigatorHolder = ciceroneLocal.getNavigatorHolder()
}