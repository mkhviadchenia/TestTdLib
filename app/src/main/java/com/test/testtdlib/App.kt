package com.test.testtdlib

import android.app.Application
import android.util.Log
import com.test.testtdlib.di.Injector
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.CheckDatabaseEncryptionKey
import org.drinkless.td.libcore.telegram.TdApi.TdlibParameters
import java.io.File

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}