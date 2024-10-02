package com.example.fitness

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MyTextWatcherMac(private val editText: EditText, private val callback: (String) -> Unit) :
    TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (s.toString().isNotEmpty()) {
            callback(s.toString())
        }
    }
}