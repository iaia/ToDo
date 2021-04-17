package dev.iaiabot.todo.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LoginUseCase
import kotlinx.coroutines.launch

sealed class Action {
    object GoToTasks : Action()
    class ShowToast(val text: String?) : Action()
}

abstract class LoginViewModel : ViewModel(), LifecycleObserver {
    abstract val routerAction: LiveData<Action>
    abstract val email: MutableLiveData<String>
    abstract val password: MutableLiveData<String>
    abstract val nowLogin: LiveData<Boolean>

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
    override val nowLogin = MutableLiveData<Boolean>(false)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        checkAlreadyLoggedIn()
    }

    override fun onClickLogin() {
        nowLogin.value = true
        viewModelScope.launch {
            try {
                loginUseCase(email.value, password.value)
            } catch (e: Exception) {
                routerAction.postValue(Action.ShowToast(e.message))
            }
            checkAlreadyLoggedIn()
        }
    }

    private fun checkAlreadyLoggedIn() {
        viewModelScope.launch {
            if (checkAlreadyLoggedInUseCase()) {
                routerAction.postValue(Action.GoToTasks)
            } else {
                nowLogin.postValue(false)
            }
        }
    }
}
