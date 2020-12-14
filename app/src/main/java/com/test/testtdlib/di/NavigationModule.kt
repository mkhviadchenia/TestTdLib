package com.test.testtdlib.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.test.testtdlib.di.annatations.SignInScope
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    @Named("Main")
    fun provideRouter(): Router = cicerone.router

    @Singleton
    @Provides
    @Named("Main")
    fun provideNavigationHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

}