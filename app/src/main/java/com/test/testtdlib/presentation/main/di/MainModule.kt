package com.test.testtdlib.presentation.main.di

import androidx.lifecycle.ViewModel
import com.test.testtdlib.di.viewmodel.ViewModelKey
import com.test.testtdlib.presentation.main.MainViewModel
import com.test.testtdlib.presentation.main.chat.ChatListFragment
import com.test.testtdlib.presentation.main.chat.ChatListViewModel
import com.test.testtdlib.presentation.main.chat.ChatViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatListViewModel::class)
    abstract fun provideChatListViewModel(viewModel: ChatListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun provideChatViewModel(viewModel: ChatViewModel): ViewModel
}