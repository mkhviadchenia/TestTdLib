package com.test.testtdlib.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.test.testtdlib.presentation.main.MainFragment
import com.test.testtdlib.presentation.main.chat.ChatFragment
import com.test.testtdlib.presentation.main.chat.ChatListFragment
import com.test.testtdlib.presentation.sign_in.SignInFragment
import com.test.testtdlib.presentation.sign_in.steps.AuthenticationCodeFragment
import com.test.testtdlib.presentation.sign_in.steps.FirstLastNameFragment
import com.test.testtdlib.presentation.sign_in.steps.PasswordFragment
import com.test.testtdlib.presentation.sign_in.steps.PhoneNumberFragment

object Screens {

    fun SignIn() = FragmentScreen { SignInFragment() }
    fun PhoneNumber() = FragmentScreen { PhoneNumberFragment() }
    fun AuthenticationCode() = FragmentScreen { AuthenticationCodeFragment() }
    fun FirstLastName() = FragmentScreen { FirstLastNameFragment() }
    fun Password() = FragmentScreen { PasswordFragment() }

    fun Main() = FragmentScreen { MainFragment() }
    fun ChatList() = FragmentScreen { ChatListFragment() }
    fun Chat(chatID: Long) = FragmentScreen { ChatFragment.newInstance(chatID) }
}