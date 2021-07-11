package dev.iaiabot.todo.login

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import dev.iaiabot.todo.HasToastAction
import dev.iaiabot.todo.HasToastActionImpl
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LoginUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.single
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
    checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
    private val loginUseCase: LoginUseCase,
) : LoginViewModel(), HasToastAction by HasToastActionImpl() {
    override val routerAction = LiveEvent<Action>()
    override val email = MutableLiveData("")
    override val password = MutableLiveData("")
    override val nowLogin = MutableLiveData<Boolean>(false)

    init {
        checkAlreadyLoggedInUseCase()
            .mapLatest { loggedIn ->
                if (loggedIn) {
                    routerAction.postValue(Action.GoToTasks)
                }
            }
            .launchIn(viewModelScope)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    override fun onClickLogin() {
        nowLogin.value = true
        viewModelScope.launch {
            try {
                val flow = loginUseCase(email.value, password.value)
                flow.single()
            } catch (e: Exception) {
                showToast(e.message)
            } finally {
                nowLogin.postValue(false)
            }
        }
    }

    override fun onClickSignUp() {
        viewModelScope.launch {
            routerAction.postValue(Action.GoToSignUp)
        }
    }
}
