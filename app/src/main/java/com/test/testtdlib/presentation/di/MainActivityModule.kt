package com.test.testtdlib.presentation.di

import androidx.lifecycle.ViewModel
import com.test.testtdlib.di.viewmodel.ViewModelKey
import com.test.testtdlib.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal  abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(viewModel: MainViewModel): ViewModel
}