package dev.iaiabot.todo.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.iaiabot.todo.R
import dev.iaiabot.todo.databinding.FragmentSignUpBinding
import dev.iaiabot.todo.observeToastAction
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.routerAction.observe(viewLifecycleOwner) {
            when (it) {
                Action.GoToTasks -> findNavController().navigate(R.id.action_sign_up_to_task)
            }
        }

        observeToastAction(viewModel)
    }
}
