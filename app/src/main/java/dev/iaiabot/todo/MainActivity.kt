package dev.iaiabot.todo

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.iaiabot.todo.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_logout -> {
                    viewModel.onClickLogout()
                    true
                }
                else -> false
            }
        }
        viewModel.loggedIn.observe(this) {
            menu?.findItem(R.id.menu_item_logout)?.isVisible = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }
}
