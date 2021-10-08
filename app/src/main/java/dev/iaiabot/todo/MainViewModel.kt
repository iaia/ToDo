package dev.iaiabot.todo

import androidx.lifecycle.*
import dev.iaiabot.todo.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.todo.usecase.user.LogoutUseCase
import kotlinx.coroutines.launch

sealed class Action {
}

abstract class MainViewModel : ViewModel(), LifecycleObserver {
    abstract val loggedIn: LiveData<Boolean>

    abstract fun onResume()
    abstract fun onClickLogout()
}

internal class MainViewModelImpl(
    checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase,
) : MainViewModel() {

    override val loggedIn: LiveData<Boolean> = checkAlreadyLoggedInUseCase().asLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    override fun onClickLogout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}
