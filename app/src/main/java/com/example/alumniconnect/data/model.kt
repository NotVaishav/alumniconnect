package com.example.alumniconnect.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "users", indices = [Index(value = ["emailId"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isStudent: Boolean = true,
    val firstName: String? = null,
    val lastName: String? = null,
    val school: String? = null,
    val program: String? = null,
    val emailId: String,
    val password: String,
    val graduationYear: Int? = null,
    val coopYear: Int? = null,
    val domain: String? = null,
)