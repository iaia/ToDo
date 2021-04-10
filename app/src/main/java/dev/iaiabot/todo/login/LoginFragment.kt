package dev.iaiabot.todo.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.iaiabot.todo.databinding.FragmentLoginBinding
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.routerAction.observe(viewLifecycleOwner) {

        }
    }
}
