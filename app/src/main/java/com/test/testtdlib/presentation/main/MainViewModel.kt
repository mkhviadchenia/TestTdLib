package com.test.testtdlib.presentation.main

import com.github.terrakok.cicerone.Router
import com.test.testtdlib.navigation.Screens
import com.test.testtdlib.presentation.base.BaseViewModel


import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(
    @Named("LocalMain") private val localRouter: Router
) : BaseViewModel() {

    init {
        localRouter.newRootChain(Screens.ChatList())
    }

}