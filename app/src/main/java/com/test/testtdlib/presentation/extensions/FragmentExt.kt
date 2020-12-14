package com.test.testtdlib.presentation.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    val viewFocusable = view?.findFocus()
    viewFocusable?.apply {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(viewFocusable.windowToken, 0)
    }
}