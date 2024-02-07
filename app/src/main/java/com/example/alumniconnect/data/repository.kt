package com.example.alumniconnect.data

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAllUsersStream(): Flow<List<User>>

    fun getUserStream(id: Int): Flow<User?>

    suspend fun insertUser(user: User): Result<Unit>

    suspend fun deleteUser(user: User)

    suspend fun updateUser(user: User)


}

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllItems()

    override fun getUserStream(id: Int): Flow<User?> = userDao.getItem(id)

    override suspend fun insertUser(user: User) : Result<Unit> {
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