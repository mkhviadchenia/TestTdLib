package com.test.testtdlib.di

import android.content.Context
import com.test.testtdlib.data.TelegramClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideTelegramClient(context: Context) = TelegramClient(context)
}