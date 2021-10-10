package dev.iaiabot.todo.feature.signup

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import dev.iaiabot.todo.commonandroid.HasToastAction
import dev.iaiabot.todo.commonandroid.HasToastActionImpl
import dev.iaiabot.todo.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.todo.usecase.user.SignUpUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

sealed class Action {
    object GoToTasks : Action()
}

abstract class SignUpViewModel : ViewModel(), LifecycleObserver, HasToastAction {
    abstract val routerAction: LiveData<Action>
    abstract val email: LiveData<String>
    abstract val password: LiveData<String>

    // TODO: 共通化
    abstract val nowLoading: LiveData<Boolean>

    abstract fun onChangeEmail(text: String)
    abstract fun onChangePassword(text: String)
    abstract fun onClickSignUp()
}

// TODO: internal にする
class SignUpViewModelImpl(
    checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
    private val signUpUseCase: SignUpUseCase,
) : SignUpViewModel(), HasToastAction by HasToastActionImpl() {
    override val routerAction = LiveEvent<Action>()
    override val email = MutableLiveData("")
    override val password = MutableLiveData("")
    override val nowLoading = MutableLiveData<Boolean>(false)

    init {
        checkAlreadyLoggedInUseCase()
            .mapLatest { loggedIn ->
                if (loggedIn) {
                    routerAction.postValue(Action.GoToTasks)
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onChangeEmail(text: String) {
        email.value = text
    }

    override fun onChangePassword(text: String) {
        password.value = text
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
