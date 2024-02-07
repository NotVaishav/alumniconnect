package com.example.alumniconnect.data

import android.content.Context

interface AppContainer {
    val usersRepository: UsersRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(AlumniConnectDatabase.getDatabase(context).userDao())
    }
}