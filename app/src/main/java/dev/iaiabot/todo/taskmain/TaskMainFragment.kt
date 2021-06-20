package dev.iaiabot.todo.taskmain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dev.iaiabot.todo.R
import dev.iaiabot.todo.databinding.FragmentTaskMainBinding

class TaskMainFragment : Fragment() {

    private val binding: FragmentTaskMainBinding
        get() = _binding!!
    private var _binding: FragmentTaskMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = TaskViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.incomplete_task)
                1 -> tab.text = getString(R.string.completed_tasks)
            }
        }.attach()
    }
}
