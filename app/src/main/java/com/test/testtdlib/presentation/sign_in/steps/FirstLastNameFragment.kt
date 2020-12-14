package com.test.testtdlib.presentation.sign_in.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.testtdlib.databinding.FragmentFirstLastNameBinding
import com.test.testtdlib.di.Injector
import com.test.testtdlib.presentation.base.BaseFragment
import com.test.testtdlib.presentation.sign_in.SignInViewModel

class FirstLastNameFragment: BaseFragment<SignInViewModel>() {

    private lateinit var binding: FragmentFirstLastNameBinding

    override fun inject() = Injector.signInComponent().inject(this)

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstLastNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.setOnClickListener {
            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            viewModel.sendFirstLastName(firstName, lastName)
        }
    }


}