package com.example.alumniconnect.data

import android.content.Context

interface AppContainer {
    val usersRepository: UsersRepository
    val domainRepository: DomainRepository
    val educationItemRepository: EducationItemRepository
    val experienceItemRepository: ExperienceItemRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(AlumniConnectDatabase.getDatabase(context).userDao())
    }
    override val domainRepository: DomainRepository by lazy {
        DomainRepository(AlumniConnectDatabase.getDatabase(context).domainDao())
    }
    override val experienceItemRepository: ExperienceItemRepository by lazy {
        ExperienceItemRepository(AlumniConnectDatabase.getDatabase(context).experienceItemDao())
    }
    override val educationItemRepository: EducationItemRepository by lazy {
        EducationItemRepository(AlumniConnectDatabase.getDatabase(context).educationItemDao())
    }

}