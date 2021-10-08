package dev.iaiabot.todo

import android.app.Application
import dev.iaiabot.todo.di.Module.appModule
import dev.iaiabot.todo.usecase.di.Module.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ToDoApplication)
            modules(listOf(useCaseModule, appModule))
        }

        /*
        Furufuru.Builder(this).settingGithub(
            "TOKEN",
            "iaia",
            "Furufuru",
        ).build()

         */
    }
}
