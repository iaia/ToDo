package dev.iaiabot.todo.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.iaiabot.todo.databinding.FragmentTaskBinding
import org.koin.android.ext.android.inject

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskViewModel by inject()
    private lateinit var incompleteTaskController: IncompleteTaskController
    private lateinit var completedTaskController: CompletedTaskController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        completedTaskController = CompletedTaskController()
        binding.ervCompletedTasks.setController(completedTaskController)
        viewModel.allCompletedTask.observe(viewLifecycleOwner) {
            completedTaskController.setData(it)
        }

        incompleteTaskController = IncompleteTaskController()
        binding.ervIncompleteTasks.setController(incompleteTaskController)
        viewModel.allIncompleteTask.observe(viewLifecycleOwner) {
            incompleteTaskController.setData(it)
        }
    }
}
