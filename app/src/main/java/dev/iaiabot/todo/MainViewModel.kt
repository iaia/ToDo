package dev.iaiabot.todo

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LogoutUseCase
import kotlinx.coroutines.launch

sealed class Action {
    object Finish : Action()
}

abstract class MainViewModel : ViewModel(), LifecycleObserver {
    abstract val loggedIn: LiveData<Boolean>
    abstract val routerAction: LiveData<Action>

    @VisibleForTesting
    abstract fun onResume()
    abstract fun onClickLogout()
}

internal class MainViewModelImpl(
    private val checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase,
) : MainViewModel() {

    override val loggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    override val routerAction: MutableLiveData<Action> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        checkAlreadyLoggedIn()
    }

    override fun onClickLogout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
            checkAlreadyLoggedIn()
            routerAction.postValue(Action.Finish)
        }
    }

    private fun checkAlreadyLoggedIn() {
        viewModelScope.launch {
            loggedIn.postValue(checkAlreadyLoggedInUseCase.invoke())
        }
    }
}
