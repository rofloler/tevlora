package com.example.tevlora.service
import com.example.tevlora.dto.PersonDto
import com.example.tevlora.dto.dtofromPerson
import com.example.tevlora.dto.personFromPersonDTO
import com.example.tevlora.model.Person
import com.example.tevlora.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PersonServiceImpl @Autowired constructor(private val personRepository: PersonRepository): PersonService {

    override fun findAllPersons(): List<PersonDto> {
       return personRepository.findAll().map{ PersonDto( it.id, lastName = it.lastName, name = it.name, age = it.age, email = it.email, password = it.password ) }
    }

    override fun findPersonById(id: Long): PersonDto{
        return personRepository.findById(id).map { PersonDto( it.id, lastName = it.lastName, name = it.name, age = it.age, email = it.email, password =  it.password) }.get()
    }

    override fun findPersonByEmail(email: String): PersonDto{
        return dtofromPerson(personRepository.findByEmail(email))
    }

    override fun addPerson(person : PersonDto): Person {
        return personRepository.save(personFromPersonDTO(person))
    }

    override fun deletePerson(person: PersonDto) {
        return personRepository.delete(personFromPersonDTO(person))
    }

    override fun updatePerson(person: PersonDto): Person {
        // TODO: Proper Implementation using DTO Builder.
        return personRepository.save(personFromPersonDTO(person))
    }

    override fun deletePersonById(personId: Long) {
        return personRepository.deleteById(personId)
    }
}