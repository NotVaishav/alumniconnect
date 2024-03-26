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
import kotlin.random.Random
import kotlin.random.nextInt


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
            val domainIds = mutableListOf<Int>()
            val domainNames = listOf(
                "Engineering", "Information Technology", "Healthcare",
                "Finance", "Marketing", "Education"
            )

            domainNames.forEach { name ->
                try {
                    val domainId = db.domainDao().insert(Domain(name = name)).toInt()
                    domainIds.add(domainId)
                } catch (e: Exception) {
                    // Handle the exception here
                    // For example, log the error or show a toast message
                    Log.e("Database", "Error inserting initial data: ${e.message}")
                }
            }
            // Generate realistic user data
            val userGenerator = RealisticUserGenerator()
            val userDataList = userGenerator.generateRealisticUserData(10, domainIds)

            // Insert your initial data here
            // For example:
            userDataList.forEach { userData ->
                try {
                    val userId = db.userDao().insert(userData.user).toInt()

                    // Insert education and experience items
                    userData.experienceItems.forEach { experienceItem ->
                        experienceItem.userId = userId
                        db.experienceItemDao().insert(experienceItem)
                    }
                    userData.educationItem.userId = userId
                    db.educationItemDao().insert(userData.educationItem)
                } catch (e: Exception) {
                    // Handle the exception here
                    // For example, log the error or show a toast message
                    Log.e("Database", "Error inserting initial data: ${e.message}")
                }
            }
        }
    }
}

class RealisticUserGenerator {
    private val firstNameList = listOf(
        "James", "John", "Robert", "Michael", "William",
        "David", "Richard", "Joseph", "Charles", "Thomas"
    )

    private val lastNameList = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones",
        "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"
    )

    private val roleList = listOf(
        "Software Developer", "Data Analyst", "Web Developer",
        "Network Engineer", "UX Designer", "Product Manager"
    )

    private val aboutPrefixList = listOf(
        "I'm a positive person.",
        "I love to travel and eat.",
        "Always available to chat.",
        "Tech enthusiast.",
        "Passionate about coding.",
        "Explorer of new technologies."
    )

    private val aboutSuffixList = listOf(
        "In my free time, I enjoy hiking and playing guitar.",
        "I'm a coffee lover and a bookworm.",
        "Big fan of outdoor activities and photography.",
        "Proud pet parent of two cats.",
        "Volunteer at local community events.",
        "Avid gamer and movie buff."
    )

    fun generateRealisticUserData(
        count: Int,
        domainIds: List<Int>
    ): List<UserData> {
        val userDataList = mutableListOf<UserData>()
        repeat(count) { index ->
            val firstName = firstNameList.random()
            val lastName = lastNameList.random()
            val email = "${firstName.toLowerCase()}.${lastName.toLowerCase()}@example.com"
            val role = roleList.random()
            val about = generateDynamicAboutSection()
            val domainId = domainIds.random()
            val socialMediaId = "@${firstName.toLowerCase()}${lastName.toLowerCase()}"
            val resumeLink =
                "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/resume%2Fr${(1..20).random()}.pdf"
            val coverLink =
                "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/Cover%20Letter%2Fc${(1..20).random()}.pdf"
            val profilePicLink =
                "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/img%2Fimg${(2..10).random()}.jpg"
            val bgPicLink =
                "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/bg%2Fbg${(1..10).random()}.jpg"
            val user = User(
                profilePic = profilePicLink,
                backGroundPic = bgPicLink,
                firstName = firstName,
                lastName = lastName,
                isStudent = true,
                emailId = email,
                password = "password",
                graduationYear = 2023,
                coopYear = 2022,
                role = role,
                about = about,
                domainId = domainId,
                isFeatured = true,
                resume = resumeLink,
                coverLetter = coverLink,
                instagramId = "$socialMediaId.instagram",
                linkedInId = "$socialMediaId.linkedin",
                facebookId = "$socialMediaId.facebook",
                contactNumber = "(123)-456-7899",
                profileHits = Random.nextInt(200, 800),
                resumeHits = Random.nextInt(50, 201),
                coverLetterHits = Random.nextInt(50, 201)
            )

            val educationItem = EducationItem(
                userId = 0, // Will be updated after user insertion
                school = "University of Example",
                degree = "Bachelor of Science in Computer Science",
                startDate = "2019-09-01",
                endDate = "2023-05-31"
            )

            val experienceItems = listOf(
                ExperienceItem(
                    userId = 0, // Will be updated after user insertion
                    role = "$role Intern",
                    company = "Tech Company XYZ",
                    startDate = "2022-05-01",
                    endDate = "2022-08-31",
                    isCoop = true
                ),
                ExperienceItem(
                    userId = 0, // Will be updated after user insertion
                    role = "$role Intern",
                    company = "Data Firm ABC",
                    startDate = "2021-05-01",
                    endDate = "2021-08-31",
                    isCoop = false
                )
            )

            userDataList.add(UserData(user, educationItem, experienceItems))
        }
        return userDataList
    }

    private fun generateDynamicAboutSection(): String {
        val prefix = aboutPrefixList.random()
        val suffix = aboutSuffixList.random()
        return "$prefix $suffix"
    }
}

data class UserData(
    val user: User,
    val educationItem: EducationItem,
    val experienceItems: List<ExperienceItem>
)