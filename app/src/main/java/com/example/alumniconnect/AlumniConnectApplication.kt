package com.example.alumniconnect


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.example.alumniconnect.data.AppContainer
import com.example.alumniconnect.data.AppDataContainer
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.alumniconnect.data.AlumniConnectDatabase
import com.example.alumniconnect.data.Domain
import com.example.alumniconnect.data.EducationItem
import com.example.alumniconnect.data.ExperienceItem
import com.example.alumniconnect.data.User
import com.example.alumniconnect.data.UserDao
import com.example.alumniconnect.data.UserDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val IS_USER_LOGGED_IN_NAME = "isUserLoggedIn"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = IS_USER_LOGGED_IN_NAME
)

class AlumniConnectApplication : Application() {


    lateinit var container: AppContainer
    lateinit var userStoreData: UserDataStore

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            applicationContext,
            AlumniConnectDatabase::class.java, "user_database"
        ).build()
        insertInitialData(db)

        container = AppDataContainer(this)
        userStoreData = UserDataStore(dataStore)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun insertInitialData(db: AlumniConnectDatabase) {
        // Perform database operations on a background thread
        GlobalScope.launch(Dispatchers.IO) {
            // Insert your initial data here
            // For example:
            var domainId = 1
            try {
                domainId = db.domainDao().insert(Domain(name = "Engineering")).toInt()

            } catch (e: Exception) {
                // Handle the exception here
                // For example, log the error or show a toast message
                Log.e("Database", "Error inserting initial data: ${e.message}")
            }
            try {
                // Insert your initial data here
                // For example:
                val user1 = User(
                    profilePic = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/profile_pic.png",
                    backGroundPic = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/profile_pic.png",
                    firstName = "John",
                    lastName = "Doe",
                    isStudent = true,
                    emailId = "john.doe@example.com",
                    password = "password",
                    graduationYear = 2023,
                    coopYear = 2022,
                    role = "Software Developer",
                    about = "I'm a positive person. I love to travel and eat. Always available to chat.",
                    domainId = domainId,
                    isFeatured = true,
                    resume = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/project-1.pdf",
                    coverLetter = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/project-1.pdf",
                    instagramId = "@test123",
                    linkedInId = "@testlinkedin",
                    facebookId = "@testfacebookid"
                )
                val userId = db.userDao().insert(user1).toInt()
                val educationItem1 = EducationItem(
                    userId = userId,
                    school = "University of Example",
                    degree = "Bachelor of Science in Computer Science",
                    startDate = "2019-09-01",
                    endDate = "2023-05-31"
                )
                val experienceItem1 = ExperienceItem(
                    userId = userId,
                    role = "Software Development Intern",
                    company = "Tech Company XYZ",
                    startDate = "2022-05-01",
                    endDate = "2022-08-31",
                    isCoop = true
                )
                db.educationItemDao().insert(educationItem1)
                db.experienceItemDao().insert(experienceItem1)
            } catch (e: Exception) {
                // Handle the exception here
                // For example, log the error or show a toast message
                Log.e("Database", "Error inserting initial data: ${e.message}")
            }
        }
    }
}