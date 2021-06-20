package dev.iaiabot.todo.task

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import dev.iaiabot.todo.databinding.FragmentTaskBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TaskFragment : Fragment() {

    companion object {
        private const val ARG_TYPE_COMPLETED = "arg_type_completed"

        fun newInstance(completed: Boolean): TaskFragment {
            return TaskFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_TYPE_COMPLETED, completed)
                }
            }
        }
    }

    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskViewModel by viewModel {
        parametersOf(arguments?.getBoolean(ARG_TYPE_COMPLETED) ?: true)
    }
    private lateinit var controller: TaskController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycle.addObserver(viewModel)

        binding = FragmentTaskBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        controller = TaskController(viewModel, ::hideKeyboard)
        binding.flexCards.setController(controller)

        viewModel.allTask.observe(viewLifecycleOwner) { tasks ->
            controller.setData(tasks)
            binding.flexCards.scrollToPosition(0)
        }
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val manager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
