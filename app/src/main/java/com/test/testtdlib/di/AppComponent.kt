package com.test.testtdlib.di

import com.test.testtdlib.App
import com.test.testtdlib.di.viewmodel.ViewModelModule
import com.test.testtdlib.presentation.di.MainActivityComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        NavigationModule::class,
        DataModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppComponent
    }

    fun plusMainComponent(): MainActivityComponent

}