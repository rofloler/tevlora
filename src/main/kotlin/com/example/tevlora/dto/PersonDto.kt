package com.example.tevlora.dto

import com.example.tevlora.model.Person
import javax.validation.constraints.Email

data class PersonDto(
                    val id: Long = 0,
                    val name: String? = null ,
                    val lastName: String? = null ,
                    val age: Int? = null,
                    val email: String = "email@email.ch",
                    val password: String = "password",

){

}

data class RegistrationDTO(
    val id: Long = 0,
    val name: String? = null,
    val lastName: String? = null,
    val age: Int? = null,

    @field:Email
    val email: String,
    val password: String
)

fun dtofromPerson(person: Person): PersonDto {
    return PersonDto( id = person.id,  person.name , person.lastName, person.age, person.email, person.password )
}

fun personFromPersonDTO(person : PersonDto): Person {
    return Person( id = person.id, name = person.name.toString(), lastName = person.lastName.toString(), age = person.age, email = person.email, password = person.password)
}
