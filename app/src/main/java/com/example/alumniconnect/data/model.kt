package com.example.alumniconnect.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(
    tableName = "domain",
    indices = [Index(value = ["name"], unique = true)],
)
data class Domain(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

@Entity(
    tableName = "users", indices = [Index(value = ["emailId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Domain::class,
            parentColumns = ["id"],
            childColumns = ["domainId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val profilePic: String? = null,
    val backGroundPic: String? = null,
    val isStudent: Boolean = true,
    val firstName: String? = null,
    val lastName: String? = null,
    val role: String? = null,
    val about: String? = null,
    val school: String? = null,
    val program: String? = null,
    val emailId: String,
    val password: String,
    val graduationYear: Int? = null,
    val coopYear: Int? = null,
    val domainId: Int? = null,
    val isFeatured: Boolean? = null,
    val resume: String? = null,
    val coverLetter: String? = null,
    val instagramId: String? = null,
    val linkedInId: String? = null,
    val facebookId: String? = null,
)


@Entity(
    tableName = "education_items",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class EducationItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    var school: String,
    var degree: String,
    var startDate: String,
    var endDate: String
)

@Entity(
    tableName = "experience_items",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExperienceItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    var role: String,
    var company: String,
    var startDate: String,
    var endDate: String,
    var isCoop: Boolean = false
)

class EducationItemListConverter {
    @TypeConverter
    fun fromList(list: List<EducationItem>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<EducationItem> {
        val type = object : TypeToken<List<EducationItem>>() {}.type
        return Gson().fromJson(json, type)
    }
}

class ExperienceItemListConverter {
    @TypeConverter
    fun fromList(list: List<ExperienceItem>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<ExperienceItem> {
        val type = object : TypeToken<List<ExperienceItem>>() {}.type
        return Gson().fromJson(json, type)
    }
}