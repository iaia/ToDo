package dev.iaiabot.todo.signup

import androidx.lifecycle.*
import dev.iaiabot.todo.HasToastAction
import dev.iaiabot.todo.HasToastActionImpl
import kotlinx.coroutines.launch

sealed class Action {
}

abstract class SignUpViewModel : ViewModel(), LifecycleObserver, HasToastAction {
    abstract val routerAction: LiveData<Action>
    abstract val email: MutableLiveData<String>
    abstract val password: MutableLiveData<String>

    abstract fun onResume()
}

internal class SignUpViewModelImpl(
) : SignUpViewModel(), HasToastAction by HasToastActionImpl() {
    // TODO: LiveEvent使う
    override val routerAction = MutableLiveData<Action>()
    override val email = MutableLiveData("")
    override val password = MutableLiveData("")

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    private fun checkAlreadyLoggedIn() {
        viewModelScope.launch {
        }
    }
}
