package com.example.tevlora.service

import com.example.tevlora.dto.PersonDto
import com.example.tevlora.model.Person

interface PersonService {
    fun findAllPersons(): List<PersonDto>

    fun findPersonById(id : Long): PersonDto

    fun findPersonByEmail(email : String): PersonDto

    fun addPerson(person: PersonDto): Person

    fun deletePerson(person: PersonDto)

    fun deletePersonById(personId: Long)

    fun updatePerson(person: PersonDto): Person
}