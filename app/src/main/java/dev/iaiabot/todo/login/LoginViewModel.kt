package dev.iaiabot.todo.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.iaiabot.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class Action {
    object GoToTasks : Action()
}

abstract class LoginViewModel : ViewModel() {
    abstract val routerAction: LiveData<Action>
    abstract val email: MutableLiveData<String>
    abstract val password: MutableLiveData<String>

    abstract fun onClickLogin()
}

internal class LoginViewModelImpl(
    private val loginUseCase: LoginUseCase,
) : LoginViewModel() {
    override val routerAction = MutableLiveData<Action>()
    override val email = MutableLiveData("")
    override val password = MutableLiveData("")

    override fun onClickLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            if (loginUseCase.invoke(email.value, password.value)) {
                routerAction.postValue(Action.GoToTasks)
            }
        }
    }
}
