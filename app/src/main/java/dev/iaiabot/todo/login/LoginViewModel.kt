package dev.iaiabot.todo.login

import androidx.lifecycle.*
import dev.iaiabot.todo.HasToastAction
import dev.iaiabot.todo.HasToastActionImpl
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LoginUseCase
import kotlinx.coroutines.launch

sealed class Action {
    object GoToTasks : Action()
    object GoToSignUp : Action()
}

abstract class LoginViewModel : ViewModel(), LifecycleObserver, HasToastAction {
    abstract val routerAction: LiveData<Action>
    abstract val email: MutableLiveData<String>
    abstract val password: MutableLiveData<String>
    abstract val nowLogin: LiveData<Boolean>

    abstract fun onResume()
    abstract fun onClickLogin()
    abstract fun onClickSignUp()
}

internal class LoginViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
) : LoginViewModel(), HasToastAction by HasToastActionImpl() {
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
                showToast(e.message)
            }
            checkAlreadyLoggedIn()
        }
    }

    override fun onClickSignUp() {
        viewModelScope.launch {
            routerAction.postValue(Action.GoToSignUp)
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
