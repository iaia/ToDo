package dev.iaiabot.todo.commonandroid

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class ToastAction {
    class ShowToast(val text: String) : ToastAction()
}

interface HasToastAction {
    val toastAction: LiveData<ToastAction>

    fun showToast(text: String?)
}

class HasToastActionImpl() : HasToastAction {
    // LiveEventを使う
    override val toastAction = MutableLiveData<ToastAction>()

    override fun showToast(text: String?) {
        if (text == null) {
            return
        }
        toastAction.postValue(ToastAction.ShowToast(text))
    }
}

fun Fragment.observeToastAction(
    hasToastAction: HasToastAction
) {
    hasToastAction.toastAction.observe(viewLifecycleOwner) {
        when (it) {
            is ToastAction.ShowToast -> Toast.makeText(context, it.text, Toast.LENGTH_LONG).show()
        }
    }
}
