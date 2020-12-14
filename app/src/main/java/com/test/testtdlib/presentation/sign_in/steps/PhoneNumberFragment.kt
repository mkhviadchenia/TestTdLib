package com.test.testtdlib.presentation.sign_in.steps

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.test.testtdlib.databinding.FragmentPhoneNumberBinding
import com.test.testtdlib.di.Injector
import com.test.testtdlib.presentation.base.BaseFragment
import com.test.testtdlib.presentation.sign_in.SignInViewModel

class PhoneNumberFragment: BaseFragment<SignInViewModel>() {

    private lateinit var binding: FragmentPhoneNumberBinding

    private var maskFullPhoneLength = 0

    override fun inject() = Injector.signInComponent().inject(this)

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneNumber)
        updateMaskPhoneLength()
        binding.phoneNumber.addTextChangedListener {
            if (it != null) {
                updateMaskPhoneLength()
                if (maskFullPhoneLength > 0) {
                    if (maskFullPhoneLength == it.length) {
                        binding.labelError.text = if (isValidNumber()) {
                            ""
                        } else {
                            "Error"
                        }
                    }
                }
            }
        }

        binding.root.setOnClickListener {
            if (isValidNumber()) {
                val phoneNumber = binding.countryCodePicker.fullNumber
                viewModel.sendPhoneNumber(phoneNumber)
            }
        }
    }

    private fun updateMaskPhoneLength() {
        maskFullPhoneLength = binding.phoneNumber.hint?.length ?: 0

        if (maskFullPhoneLength > 0) {
            binding.phoneNumber.filters = arrayOf(InputFilter.LengthFilter(maskFullPhoneLength))
        }
    }

    private fun isValidNumber(): Boolean {
        return binding.countryCodePicker.isValidFullNumber
    }

}

