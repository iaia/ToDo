package dev.iaiabot.todo.bindingadapter

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.BindingAdapter

interface OnOkInSoftKeyboardListener {
    fun onOkInSoftKeyboard()
}

object EditTextBindingAdapter {
    @BindingAdapter("onOkInSoftKeyboard")
    @JvmStatic
    fun TextView.setOnOkInSoftKeyboardListener(listener: OnOkInSoftKeyboardListener) {
        setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    listener.onOkInSoftKeyboard()
                }
                return@OnEditorActionListener true
            }
        )
    }
}
