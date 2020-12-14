package com.test.testtdlib.presentation.sign_in.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.testtdlib.databinding.FragmentPasswordBinding
import com.test.testtdlib.di.Injector
import com.test.testtdlib.presentation.base.BaseFragment
import com.test.testtdlib.presentation.sign_in.SignInViewModel

class PasswordFragment: BaseFragment<SignInViewModel>() {

    private lateinit var binding: FragmentPasswordBinding

    override fun inject() = Injector.signInComponent().inject(this)

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        TODO
        binding.root.setOnClickListener {
            val password = binding.password.text.toString()
            viewModel.sendPassword(password)
        }
    }


}