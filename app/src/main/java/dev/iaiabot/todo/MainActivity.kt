package dev.iaiabot.todo

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import dev.iaiabot.todo.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_logout -> {
                    viewModel.onClickLogout()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        val logoutMenu = menu?.findItem(R.id.menu_item_logout)
        logoutMenu?.isVisible = false
        viewModel.loggedIn.observe(this) {
            logoutMenu?.isVisible = it
            signOuted()
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun signOuted() {
        findNavController(binding.fcvContainer.id).run {
            navigate(R.id.action_sign_out)
        }
    }
}
