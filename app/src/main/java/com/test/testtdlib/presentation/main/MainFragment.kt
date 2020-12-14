package com.test.testtdlib.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.test.testtdlib.R
import com.test.testtdlib.databinding.FragmentMainBinding
import com.test.testtdlib.di.Injector
import com.test.testtdlib.presentation.base.BaseFragment
import javax.inject.Inject
import javax.inject.Named

class MainFragment : BaseFragment<MainViewModel>() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    @Named("LocalMain")
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), R.id.frame_content_1, childFragmentManager)
    }

    override fun inject() = Injector.mainComponent().inject(this)

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        drawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawer,
            binding.toolbar,
            R.string.app_name,
            R.string.app_name
        )
        binding.drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }


    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}