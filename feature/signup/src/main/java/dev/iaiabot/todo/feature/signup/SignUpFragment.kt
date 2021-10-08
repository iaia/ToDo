package dev.iaiabot.todo.feature.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.iaiabot.todo.commonandroid.observeToastAction

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel

    /*
    private lateinit var binding: FragmentSignUpBinding
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
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        viewModel.routerAction.observe(viewLifecycleOwner) {
            when (it) {
                Action.GoToTasks -> findNavController().navigate(R.id.action_sign_up_to_task)
            }
        }
         */

        observeToastAction(viewModel)
    }
}
