package com.example.alumniconnect.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [User::class, EducationItem::class, ExperienceItem::class, Domain::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(EducationItemListConverter::class, ExperienceItemListConverter::class)

abstract class AlumniConnectDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun educationItemDao(): EducationItemDao
    abstract fun experienceItemDao(): ExperienceItemDao
    abstract fun domainDao(): DomainDao

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