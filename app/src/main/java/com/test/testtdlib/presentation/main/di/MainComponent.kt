package com.test.testtdlib.presentation.main.di

import com.test.testtdlib.presentation.main.MainFragment
import com.test.testtdlib.presentation.main.chat.ChatFragment
import com.test.testtdlib.presentation.main.chat.ChatListFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        MainModule::class,
        MainNavigationModule::class
    ]
)
interface MainComponent {

    fun inject(fragment: MainFragment)

    fun inject(fragment: ChatListFragment)
    fun inject(fragment: ChatFragment)
}