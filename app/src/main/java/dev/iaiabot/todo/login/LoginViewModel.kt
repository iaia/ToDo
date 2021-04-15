package dev.iaiabot.todo.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.usecase.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.LoginUseCase
import kotlinx.coroutines.launch

sealed class Action {
    object GoToTasks : Action()
}

abstract class LoginViewModel : ViewModel(), LifecycleObserver {
    abstract val routerAction: LiveData<Action>
    abstract val email: MutableLiveData<String>
    abstract val password: MutableLiveData<String>

    @VisibleForTesting
    abstract fun onResume()
    abstract fun onClickLogin()
}

internal class LoginViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
) : LoginViewModel() {
    // TODO: LiveEvent使う
    override val routerAction = MutableLiveData<Action>()
    override val email = MutableLiveData("")
    override val password = MutableLiveData("")

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        checkAlreadyLoggedIn()
    }

    override fun onClickLogin() {
        viewModelScope.launch {
            loginUseCase.invoke(email.value, password.value)
            checkAlreadyLoggedIn()
        }
    }

    private fun checkAlreadyLoggedIn() {
        viewModelScope.launch {
            if (checkAlreadyLoggedInUseCase.invoke()) {
                routerAction.postValue(Action.GoToTasks)
            }
        }
    }
}
