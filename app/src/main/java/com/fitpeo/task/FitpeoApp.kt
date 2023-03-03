package com.fitpeo.task

import android.app.Application
import com.fitpeo.task.modules.activity.activityModule
import com.fitpeo.task.modules.network.apiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FitpeoApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                apiModule,
                activityModule
            )
        }

    }

}