package com.test.testtdlib.presentation.sign_in.di

import com.test.testtdlib.di.annatations.SignInScope
import com.test.testtdlib.presentation.sign_in.SignInFragment
import com.test.testtdlib.presentation.sign_in.steps.AuthenticationCodeFragment
import com.test.testtdlib.presentation.sign_in.steps.FirstLastNameFragment
import com.test.testtdlib.presentation.sign_in.steps.PasswordFragment
import com.test.testtdlib.presentation.sign_in.steps.PhoneNumberFragment
import dagger.Subcomponent

@SignInScope
@Subcomponent(modules = [
    SignInModule::class,
    SignInNavigationModule::class
])
interface SignInComponent {
    fun inject(fragment: SignInFragment)
    fun inject(fragment: PhoneNumberFragment)
    fun inject(fragment: AuthenticationCodeFragment)
    fun inject(fragment: FirstLastNameFragment)
    fun inject(fragment: PasswordFragment)
}