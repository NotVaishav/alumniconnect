package com.example.alumniconnect.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE id = :id")
    fun getItem(id: Int): Flow<User>

    @Query("SELECT * from users ORDER BY id ASC")
    fun getAllItems(): Flow<List<User>>

    @Query("SELECT * from users WHERE emailId = :emailId")
    fun getUserByEmail(emailId: String): Flow<User>
}

@Dao
interface EducationItemDao {
    @Query("SELECT * FROM education_items")
    fun getAllEducationItems(): Flow<List<EducationItem>>

    @Query("SELECT * FROM education_items WHERE userId= :id")
    fun getEducationForUser(id: Int): Flow<List<EducationItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(educationItem: EducationItem)

    @Update
    suspend fun update(educationItem: EducationItem)

    // Add other DAO functions as needed
}

@Dao
interface ExperienceItemDao {
    @Query("SELECT * FROM experience_items")
    fun getAllExperienceItems(): Flow<List<ExperienceItem>>

    @Query("SELECT * FROM experience_items WHERE userId= :id")
    fun getExperienceForUser(id: Int): Flow<List<ExperienceItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(experienceItem: ExperienceItem)

    @Update
    suspend fun update(experienceItem: ExperienceItem)

    // Add other DAO functions as needed
}

@Dao
interface DomainDao {
    @Query("SELECT * FROM domain")
    fun getAllDomains(): Flow<List<Domain>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(domain: Domain): Long
}