package com.example.alumniconnect.data

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAllUsersStream(): Flow<List<User>>

    fun getUserStream(id: Int): Flow<User?>

    fun getUserByEmailStream(emailId: String): Flow<User?>


    suspend fun insertUser(user: User): Result<Unit>

    suspend fun deleteUser(user: User)

    suspend fun updateUser(user: User)


}

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllItems()

    override fun getUserStream(id: Int): Flow<User?> = userDao.getItem(id)

    override fun getUserByEmailStream(emailId: String): Flow<User?> =
        userDao.getUserByEmail(emailId)

    override suspend fun insertUser(user: User): Result<Unit> {
        return try {
            userDao.insert(user)
            Result.success(Unit)
        } catch (e: SQLiteConstraintException) {
            // Handle the constraint violation error here
            // You can choose to log the error, show a message to the user, or handle it as needed
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)
}

class EducationItemRepository(private val educationItemDao: EducationItemDao) {
    val allEducationItems: Flow<List<EducationItem>> = educationItemDao.getAllEducationItems()

    suspend fun insert(educationItem: EducationItem): Result<Unit> {
        return try {
            educationItemDao.insert(educationItem)
            Result.success(Unit)
        } catch (e: SQLiteConstraintException) {
            Result.failure(e)
        }
    }

    suspend fun updateEducationItem(educationItem: EducationItem) =
        educationItemDao.update(educationItem)

    suspend fun getEducationForUser(userId: Int): Flow<List<EducationItem>> =
        educationItemDao.getEducationForUser(userId)

    // Add other functions as needed
}

class ExperienceItemRepository(private val experienceItemDao: ExperienceItemDao) {
    val allExperienceItems: Flow<List<ExperienceItem>> = experienceItemDao.getAllExperienceItems()

    suspend fun insert(experienceItem: ExperienceItem): Result<Unit> {
        return try {
            experienceItemDao.insert(experienceItem)
            Result.success(Unit)
        } catch (e: SQLiteConstraintException) {
            Result.failure(e)
        }
    }

    suspend fun updateExperienceItem(experienceItem: ExperienceItem) =
        experienceItemDao.update(experienceItem)

    suspend fun getExperienceForUser(id: Int): Flow<List<ExperienceItem>> =
        experienceItemDao.getExperienceForUser(id)

    // Add other functions as needed
}


class DomainRepository(private val domainDao: DomainDao) {
    fun getAllDomains(): Flow<List<Domain>> = domainDao.getAllDomains()
}