package dev.iaiabot.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import dev.iaiabot.todo.component.MyAppBar
import dev.iaiabot.todo.component.MyTheme
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    // private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainContent {
            }
        }

        /*
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
         */
    }

    /*
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
     */
}

@Composable
fun MainContent(
    bodyContent: @Composable () -> Unit
) {
    MyTheme {
        Scaffold(
            topBar = { MyAppBar() },
            content = { bodyContent.invoke() }
        )
    }
}
