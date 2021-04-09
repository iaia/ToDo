package dev.iaiabot.todo

import android.app.Application
import dev.iaiabot.todo.di.Module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ToDoApplication)
            modules(Module.appModule)
        }
    }
}
