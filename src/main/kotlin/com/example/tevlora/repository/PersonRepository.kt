package com.example.tevlora.repository

import com.example.tevlora.model.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long>{

    fun findByEmail(string: String): Person

}
