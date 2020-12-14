package com.test.testtdlib.presentation.sign_in.di

import androidx.lifecycle.ViewModel
import com.test.testtdlib.di.annatations.SignInScope
import com.test.testtdlib.di.viewmodel.ViewModelKey
import com.test.testtdlib.presentation.sign_in.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class SignInModule {

    @SignInScope
    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun bindViewModel(viewModel: SignInViewModel): ViewModel
}