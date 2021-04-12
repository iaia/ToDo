package dev.iaiabot.todo

import androidx.lifecycle.*
import dev.iaiabot.usecase.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel(), LifecycleObserver {
    private val _loggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    val loggedIn: LiveData<Boolean> = _loggedIn

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        checkAlreadyLoggedIn()
    }

    fun onClickLogout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }

    private fun checkAlreadyLoggedIn() {
        viewModelScope.launch {
            _loggedIn.postValue(checkAlreadyLoggedInUseCase.invoke())
        }
    }
}
