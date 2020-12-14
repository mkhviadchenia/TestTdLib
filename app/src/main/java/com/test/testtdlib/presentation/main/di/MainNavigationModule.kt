package com.test.testtdlib.presentation.main.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainNavigationModule {

    private val ciceroneLocal: Cicerone<Router> = Cicerone.create()

    @Provides
    @Named("LocalMain")
    fun provideLocalRouter(): Router = ciceroneLocal.router

    @Provides
    @Named("LocalMain")
    fun provideLocalNavigationHolder(): NavigatorHolder = ciceroneLocal.getNavigatorHolder()
}