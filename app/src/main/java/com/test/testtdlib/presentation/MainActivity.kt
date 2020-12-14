package com.test.testtdlib.presentation

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.test.testtdlib.R
import com.test.testtdlib.databinding.ActivityMainBinding
import com.test.testtdlib.di.Injector
import com.test.testtdlib.presentation.base.BaseActivity
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var binding: ActivityMainBinding

    @Named("Main")
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.main_content)

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun injectActivity() = Injector.mainActivityComponent().inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        if (isFinishing) {
            Injector.clearMainActivityComponent()
        }
        super.onDestroy()
    }
}