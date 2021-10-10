package dev.iaiabot.todo.feature.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.iaiabot.todo.commonandroid.observeToastAction

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel

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
