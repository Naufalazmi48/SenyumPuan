package com.example.senyumpuan.utils

import android.annotation.SuppressLint
import android.text.method.ScrollingMovementMethod
import android.widget.EditText
import android.widget.TextView
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

    @SuppressLint("ClickableViewAccessibility")
    fun TextView.setScrollable() {
        movementMethod = ScrollingMovementMethod()

        setOnTouchListener { _, _ ->
            this.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    fun formErrorHandler(viewInput: TextInputLayout, errorCondition: Boolean, errorMessage: String){
        if (errorCondition) viewInput.error = errorMessage
    }
}