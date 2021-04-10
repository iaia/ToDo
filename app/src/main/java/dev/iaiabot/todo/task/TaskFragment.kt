package dev.iaiabot.todo.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class TaskFragment : Fragment() {
    lateinit var controller: TaskController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = TaskController()
    }
}

