package dev.iaiabot.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.iaiabot.entity.Task

interface GetAllIncompleteTaskUseCase {
    suspend operator fun invoke(): LiveData<List<Task>>
}

internal class GetAllIncompleteTaskUseCaseImpl : GetAllIncompleteTaskUseCase {
    override suspend fun invoke(): LiveData<List<Task>> {
        return MutableLiveData(emptyList())
    }
}

