package com.example.core.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

object Helper {

    fun EditText.setAutoClearError(viewInput: TextInputLayout){
        addTextChangedListener {
            if (it.toString().isNotEmpty() && viewInput.error != null) {
                viewInput.error = null
            }
        }
    }

    fun formErrorHandler(viewInput: TextInputLayout, errorCondition: Boolean, errorMessage: String){
        if (errorCondition) viewInput.error = errorMessage
    }
}