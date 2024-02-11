package com.example.alumniconnect


import android.app.Application
import com.example.alumniconnect.data.AppContainer
import com.example.alumniconnect.data.AppDataContainer

class AlumniConnectApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)
    }
}