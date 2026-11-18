package com.example.proyectoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val company: String,
    val preferredDate: String,
    val isInPersonMeeting: Boolean,
    val message: String
)