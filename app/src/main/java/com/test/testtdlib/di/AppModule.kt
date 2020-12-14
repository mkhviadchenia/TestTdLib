package com.test.testtdlib.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.test.testtdlib.App
import com.test.testtdlib.di.viewmodel.ViewModelKey
import com.test.testtdlib.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: App): Context = application.applicationContext!!

}