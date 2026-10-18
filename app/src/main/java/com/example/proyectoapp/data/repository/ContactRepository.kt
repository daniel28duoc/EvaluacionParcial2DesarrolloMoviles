package com.example.proyectoapp.data.repository

import com.example.proyectoapp.data.local.ContactDao
import com.example.proyectoapp.data.local.ContactEntity
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {
    suspend fun saveContact(contact: ContactEntity) {
        contactDao.insertContact(contact)
    }

    fun getAllContacts(): Flow<List<ContactEntity>> = contactDao.getAllContacts()
}