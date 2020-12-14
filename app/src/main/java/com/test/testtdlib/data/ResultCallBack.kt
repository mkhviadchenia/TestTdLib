package com.test.testtdlib.data

interface ResultCallBack {

    fun onOk()

    fun onError(message: String)
}