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

interface LoginViewModel {
    val routerAction: LiveData<Action>
    val email: LiveData<String>
    val password: LiveData<String>

    fun onClickLogin()
}

internal class LoginViewModelImpl(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), LoginViewModel {
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
