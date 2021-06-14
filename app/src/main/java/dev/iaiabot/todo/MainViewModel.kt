package dev.iaiabot.todo

import androidx.lifecycle.*
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LogoutUseCase
import kotlinx.coroutines.launch

sealed class Action {
}

abstract class MainViewModel : ViewModel(), LifecycleObserver {
    abstract val loggedIn: LiveData<Boolean>

    abstract fun onResume()
    abstract fun onClickLogout()
}

internal class MainViewModelImpl(
    private val checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
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
