package dev.iaiabot.todo.login

import androidx.lifecycle.*
import dev.iaiabot.todo.HasToastAction
import dev.iaiabot.todo.HasToastActionImpl
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LoginUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
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
    private val loggedIn = checkAlreadyLoggedInUseCase()
        .mapLatest { loggedIn ->
            if (loggedIn) {
                routerAction.postValue(Action.GoToTasks)
            } else {
                nowLogin.postValue(false)
            }
            loggedIn
        }
        .shareIn(viewModelScope, replay = 1, started = SharingStarted.Eagerly)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    override fun onClickLogin() {
        nowLogin.value = true
        viewModelScope.launch {
            try {
                loginUseCase(email.value, password.value)
            } catch (e: Exception) {
                showToast(e.message)
            }
        }
    }

    override fun onClickSignUp() {
        viewModelScope.launch {
            routerAction.postValue(Action.GoToSignUp)
        }
    }
}
