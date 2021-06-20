package dev.iaiabot.todo.taskmain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.iaiabot.todo.task.TaskFragment

class TaskViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TaskFragment.newInstance(true)
            else -> TaskFragment.newInstance(false)
        }
    }
}
