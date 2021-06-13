package dev.iaiabot.todo.signup

import androidx.lifecycle.*
import dev.iaiabot.todo.HasToastAction
import dev.iaiabot.todo.HasToastActionImpl
import dev.iaiabot.usecase.user.SignUpUseCase
import kotlinx.coroutines.launch

sealed class Action {
}

abstract class SignUpViewModel : ViewModel(), LifecycleObserver, HasToastAction {
    abstract val routerAction: LiveData<Action>
    abstract val email: MutableLiveData<String>
    abstract val password: MutableLiveData<String>

    abstract fun onResume()
    abstract fun onClickSignUp()
}

internal class SignUpViewModelImpl(
    private val signUpUseCase: SignUpUseCase,
) : SignUpViewModel(), HasToastAction by HasToastActionImpl() {
    // TODO: LiveEvent使う
    override val routerAction = MutableLiveData<Action>()
    override val email = MutableLiveData("")
    override val password = MutableLiveData("")

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    override fun onClickSignUp() {
        viewModelScope.launch {
            signUpUseCase(email.value, password.value)
        }
    }
}
