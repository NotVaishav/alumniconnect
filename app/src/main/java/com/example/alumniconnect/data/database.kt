package com.example.alumniconnect.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AlumniConnectDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: AlumniConnectDatabase? = null
        fun getDatabase(context: Context): AlumniConnectDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlumniConnectDatabase::class.java, "user_database")
                    .build().also { Instance = it }
            }
        }
    }
}