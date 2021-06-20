package dev.iaiabot.todo.signup

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import dev.iaiabot.todo.HasToastAction
import dev.iaiabot.todo.HasToastActionImpl
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.SignUpUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

sealed class Action {
    object GoToTasks : Action()
}

abstract class SignUpViewModel : ViewModel(), LifecycleObserver, HasToastAction {
    abstract val routerAction: LiveData<Action>
    abstract val email: MutableLiveData<String>
    abstract val password: MutableLiveData<String>

    abstract fun onResume()
    abstract fun onClickSignUp()
}

internal class SignUpViewModelImpl(
    checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
    private val signUpUseCase: SignUpUseCase,
) : SignUpViewModel(), HasToastAction by HasToastActionImpl() {
    override val routerAction = LiveEvent<Action>()
    override val email = MutableLiveData("")
    override val password = MutableLiveData("")
    private val loggedIn = checkAlreadyLoggedInUseCase()
        .mapLatest { loggedIn ->
            if (loggedIn) {
                routerAction.postValue(Action.GoToTasks)
            }
        }
        .shareIn(viewModelScope, replay = 1, started = SharingStarted.Eagerly)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    override fun onClickSignUp() {
        viewModelScope.launch {
            try {
                signUpUseCase(email.value, password.value)
                // TODO: メール送りましたトースト出す
            } catch (e: Exception) {
                // TODO: toast出す
            }
        }
    }
}
